package ar.modularsoft.api.resources;

import ar.modularsoft.api.dtos.CompanyMenuCRUDDto;
import ar.modularsoft.api.dtos.CompanyMenuDto;
import ar.modularsoft.api.dtos.CompanyMenuRawDto;
import ar.modularsoft.api.dtos.MenuDto;
import ar.modularsoft.data.model.CompanyMenu;
import ar.modularsoft.data.model.CompanyMenuId;
import ar.modularsoft.dto.ApiResponse;
import ar.modularsoft.exception.UserAlreadyExistAuthenticationException;
import ar.modularsoft.services.CompanyMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Api(tags = "Menu Empresas")

@RestController
@RequestMapping(CompanyMenuResource.COMPANY_MENU)
public class CompanyMenuResource {
    public static final String COMPANY_MENU = "/company-menu";
    public static final String SAVE = "/save";
    public static final String UPDATE = "/update";
    public static final String LIST = "/list";

    private final CompanyMenuService companyMenuService;

    @Autowired
    public CompanyMenuResource(CompanyMenuService companyMenuService) {
        this.companyMenuService = companyMenuService;
    }

    @ApiOperation(value = "Obtiene el menu de navegacion de la empresa")
    @PreAuthorize("permitAll()")
    @GetMapping()
    public Stream<MenuDto> getCompanyMenuById(@RequestParam(defaultValue = "", required = false) Integer id) {
        List<MenuDto> menuDtoList = companyMenuService.getCompanyMenuById(id)
                .map(CompanyMenuDto::toMenuDto).collect(Collectors.toList());

        menuDtoList.sort(Comparator.comparingInt(MenuDto::getOrder));
        menuDtoList.sort(Comparator.comparingInt(MenuDto::getParentId).reversed());

        List<MenuDto> menuDtoListTree = menuDtoList;

        for (MenuDto item : menuDtoList
        ) {
            MenuDto menuDtoAux = item;

            for (MenuDto item1 : menuDtoList
            ) {
                if (item.getParentId() == item1.getId()) {
                    List<MenuDto> submenu = item1.getSubmenu() != null ? item1.getSubmenu() : new ArrayList<>();
                    submenu.add(item);
                    item1.setSubmenu(submenu);
                }
            }
        }
        menuDtoList.removeIf(menuDto -> menuDto.getParentId() != 0);
        return menuDtoList.stream();
    }

    @ApiOperation(value = "Agrega menu a la emrpesa | Add menu to company")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping()
    public ResponseEntity<?> saveMenu(@RequestBody CompanyMenuCRUDDto companyMenuCRUDDto) {
        try {

            companyMenuService.save(companyMenuCRUDDto.toCompanyMenuCRUD(companyMenuCRUDDto));
            return ResponseEntity.ok(HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Exception Ocurred", e);
            return new ResponseEntity<>(new ApiResponse(false, "Error to save menu"), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "Actualiza menu a la emrpesa | Update menu to company")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping()
    public ResponseEntity<?> update(@RequestBody CompanyMenuCRUDDto companyMenuCRUDDto) {
        try {
            // CompanyMenuDto companyMenuDto = CompanyMenuD
            companyMenuService.save(companyMenuCRUDDto.toCompanyMenuCRUD(companyMenuCRUDDto));
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (UserAlreadyExistAuthenticationException e) {
            log.error("Exception Ocurred", e);
            return new ResponseEntity<>(new ApiResponse(false, "Error to update menu"), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "Borra menu de navegacion | Delete navigation menu")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping()
    public ResponseEntity<?> delete(@RequestBody CompanyMenuId id) {
        try {
            companyMenuService.deleteById(id);
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (UserAlreadyExistAuthenticationException e) {
            log.error("Exception Ocurred", e);
            return new ResponseEntity<>(new ApiResponse(false, "Error to delete menu"), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(LIST)
    public Stream<CompanyMenuRawDto> list() {
        return companyMenuService.readAll().map(CompanyMenuRawDto::new);
    }
}
