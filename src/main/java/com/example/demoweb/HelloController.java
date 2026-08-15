package com.example.demoweb;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HelloController {
    @GetMapping("/property")
    public String showProperty(
            @RequestParam(value = "sort", required = false) String sort,
            @RequestParam(value = "keyword", required = false) String keyword,
            Model model) {

        List<Property> propertyList = createPropertyList();

        if (keyword != null && !keyword.isBlank()) {
            propertyList = propertyList.stream()
                    .filter(p -> p.getName().contains(keyword) || p.getLocation().contains(keyword))
                    .collect(Collectors.toList());
        }

        Comparator<Property> byPrice = Comparator.comparingInt(p -> parsePrice(p.getPrice()));

        if ("asc".equals(sort)) {
            propertyList.sort(byPrice);
        } else if ("desc".equals(sort)) {
            propertyList.sort(byPrice.reversed());
        }

        model.addAttribute("properties", propertyList);
        model.addAttribute("currentSort", sort);
        model.addAttribute("currentKeyword", keyword);

        return "property";
    }

    private List<Property> createPropertyList() {
        List<Property> propertyList = new ArrayList<>();

        propertyList.add(new Property(1, "パークタワー小沢 25階", "185,000円", "東京都港区六本木", "2LDK",
                "六本木駅", "徒歩5分", "築8年"));
        propertyList.add(new Property(2, "サンハイツ赤坂 3階", "92,000円", "東京都港区赤坂", "1K",
                "赤坂駅", "徒歩3分", "築15年"));
        propertyList.add(new Property(3, "グリーンコーポ渋谷 1階", "115,000円", "東京都渋谷区道玄坂", "1DK",
                "渋谷駅", "徒歩7分", "築12年"));
        propertyList.add(new Property(4, "ライオンズマンション新宿 8階", "140,000円", "東京都新宿区歌舞伎町", "1LDK",
                "新宿駅", "徒歩4分", "築5年"));

        return propertyList;
    }

    @GetMapping("/property/{id}")
    public String showPropertyDetail(@PathVariable int id, Model model){
        List<Property> propertyList = createPropertyList();

        Property target = propertyList.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);

        model.addAttribute("property", target);

        return "property-detail";
    }

    private int parsePrice(String priceText) {
        String numberOnly = priceText.replace(",", "").replace("円", "");
        return Integer.parseInt(numberOnly);
    }
}