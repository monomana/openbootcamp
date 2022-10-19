package ar.modularsoft.api.dtos;

import ar.modularsoft.data.model.Company;
import ar.modularsoft.data.model.CompanyMenu;
import ar.modularsoft.data.model.Menu;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MenuDto {

    private int id;
    private String name;
    private String url;
    private String icon;
    private int parentId;
    private int order;
    private List<MenuDto> submenu;

//    public MenuDto(CompanyMenu companyMenu) {
//        if (companyMenu.getMenu() != null){
//            BeanUtils.copyProperties(companyMenu.getMenu(), this);
//        }
//    }

    public MenuDto(Menu menu) {
        BeanUtils.copyProperties(menu, this);
    }

    public Menu toMenu(MenuDto menuDto) {
        Menu menu = new Menu();
        BeanUtils.copyProperties(this, menu);
        return menu;
    }
}
