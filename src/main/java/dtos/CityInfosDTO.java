package dtos;

import entities.CityInfo;

import java.util.ArrayList;
import java.util.List;

public class CityInfosDTO {

    List<CityInfoDTO> cityInfoDTOS = new ArrayList<>();

    public CityInfosDTO(List<CityInfo> cityInfos){
        cityInfos.forEach((c) ->{
            cityInfoDTOS.add(new CityInfoDTO(c));
        });
    }
    public List<CityInfoDTO> getAll(){
        return cityInfoDTOS;
    }

    @Override
    public String toString() {
        return "CityInfosDTO{" +
                "cityInfoDTOS=" + cityInfoDTOS +
                '}';
    }
}
