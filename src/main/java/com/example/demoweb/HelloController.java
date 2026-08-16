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

    @GetMapping("/")
    public String home() {
        return "redirect:/property";
    }

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

        propertyList.add(new Property(1, "パークタワー六本木 25階", "185,000円", "東京都港区六本木", "2LDK", "六本木駅", "徒歩5分", "築8年"));
        propertyList.add(new Property(2, "サンハイツ赤坂 3階", "92,000円", "東京都港区赤坂", "1K", "赤坂駅", "徒歩3分", "築15年"));
        propertyList.add(new Property(3, "グリーンコーポ渋谷 1階", "115,000円", "東京都渋谷区道玄坂", "1DK", "渋谷駅", "徒歩7分", "築12年"));
        propertyList.add(new Property(4, "ライオンズマンション新宿 8階", "140,000円", "東京都新宿区歌舞伎町", "1LDK", "新宿駅", "徒歩4分", "築5年"));
        propertyList.add(new Property(5, "中野レジデンス 4階", "78,000円", "東京都中野区中野", "1K", "中野駅", "徒歩6分", "築20年"));
        propertyList.add(new Property(6, "メゾン三軒茶屋", "98,000円", "東京都世田谷区三軒茶屋", "1DK", "三軒茶屋駅", "徒歩8分", "築10年"));
        propertyList.add(new Property(7, "武蔵小杉スカイレジデンス 12階", "168,000円", "神奈川県川崎市中原区", "2DK", "武蔵小杉駅", "徒歩5分", "築3年"));
        propertyList.add(new Property(8, "コーポ吉祥寺", "82,000円", "東京都武蔵野市吉祥寺本町", "1K", "吉祥寺駅", "徒歩9分", "築18年"));
        propertyList.add(new Property(9, "パークハイム目黒", "220,000円", "東京都目黒区目黒", "2LDK", "目黒駅", "徒歩4分", "築7年"));
        propertyList.add(new Property(10, "レジデンス下北沢", "105,000円", "東京都世田谷区北沢", "1LDK", "下北沢駅", "徒歩6分", "築11年"));
        propertyList.add(new Property(11, "ファミーユ荻窪", "135,000円", "東京都杉並区荻窪", "2DK", "荻窪駅", "徒歩5分", "築14年"));
        propertyList.add(new Property(12, "メゾン浅草", "72,000円", "東京都台東区浅草", "1K", "浅草駅", "徒歩10分", "築25年"));
        propertyList.add(new Property(13, "グランドメゾン品川", "260,000円", "東京都港区港南", "3LDK", "品川駅", "徒歩3分", "築2年"));
        propertyList.add(new Property(14, "コーポラス自由が丘", "128,000円", "東京都目黒区自由が丘", "1LDK", "自由が丘駅", "徒歩7分", "築9年"));
        propertyList.add(new Property(15, "サンライズ北千住", "68,000円", "東京都足立区千住", "1K", "北千住駅", "徒歩8分", "築22年"));
        propertyList.add(new Property(16, "パークコート二子玉川", "195,000円", "東京都世田谷区玉川", "2LDK", "二子玉川駅", "徒歩6分", "築4年"));


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