package ar.modularsoft.api.resources;

import ar.modularsoft.api.dtos.MenuDto;
import ar.modularsoft.data.model.CompanyMenuId;
import ar.modularsoft.dto.ApiResponse;
import ar.modularsoft.exception.UserAlreadyExistAuthenticationException;
import ar.modularsoft.services.CompanyMenuService;
import ar.modularsoft.services.MenuService;
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
@Api( tags = "Menu")
@PreAuthorize("hasRole('ADMIN')")
@RestController
@RequestMapping(MenuResource.MENU)
public class MenuResource {
    public static final String MENU = "/menu";
    public static final String SAVE = "/save";
    public static final String UPDATE = "/update";

    private final MenuService menuService;

    @Autowired
    public MenuResource(MenuService menuService) {
        this.menuService = menuService;
    }

    @ApiOperation(value = "Obtiene listado de menu de navegacion")
    @GetMapping()
    public Stream<MenuDto>getMenu(
    ){
     List<MenuDto> menuDtoList = menuService.readAll()
        .map(MenuDto::new).collect(Collectors.toList());

        menuDtoList.sort(Comparator.comparingInt(MenuDto::getOrder));
        menuDtoList.sort(Comparator.comparingInt(MenuDto::getParentId).reversed());

        List<MenuDto> menuDtoListTree =menuDtoList;

        for (MenuDto item: menuDtoList) {
            for (MenuDto item1: menuDtoList) {
                if(item.getParentId() == item1.getId()){
                    List<MenuDto> submenu= item1.getSubmenu() != null ?item1.getSubmenu() : new ArrayList<>();
                    submenu.add(item);
                   item1.setSubmenu(submenu);
                }
            }
        }
        menuDtoList.removeIf(menuDto -> menuDto.getParentId() != 0);
            return  menuDtoList.stream();
    }

    @ApiOperation(value = "Agrega un menu de navegacion | Add navigation menu")
    @PostMapping()
    public ResponseEntity<?>  saveMenu(@RequestBody MenuDto menuDto){
       try {
           menuService.save(menuDto.toMenu(menuDto));
           return  ResponseEntity.ok(HttpStatus.CREATED);
       } catch (UserAlreadyExistAuthenticationException e) {
           log.error("Exception Ocurred", e);
           return new ResponseEntity<>(new ApiResponse(false, "Error to save menu"), HttpStatus.BAD_REQUEST);
       }
    }

    @ApiOperation(value = "Actualiza menu a la emrpesa | Update menu to company")
    @PutMapping()
    public ResponseEntity<?> update(@RequestBody MenuDto menuDto){
        try {
            menuService.save(menuDto.toMenu(menuDto));
        return  ResponseEntity.ok(HttpStatus.OK);
    } catch (UserAlreadyExistAuthenticationException e) {
        log.error("Exception Ocurred", e);
        return new ResponseEntity<>(new ApiResponse(false, "Error to update menu"), HttpStatus.BAD_REQUEST);
    }
    }

    @ApiOperation(value = "Borra menu a la emrpesa | Delete menu to company")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        try {
            menuService.deleteById(id);
            return ResponseEntity.ok(HttpStatus.OK);
        }
        catch (UserAlreadyExistAuthenticationException e) {
            log.error("Exception Ocurred", e);
            return new ResponseEntity<>(new ApiResponse(false, "Error to delete menu"), HttpStatus.BAD_REQUEST);
        }
    }
}
