package com.example.sstep.commute;

import com.example.sstep.photo_api.PhotoResponseDto;

import java.util.ArrayList;
import java.util.Set;

public class CommuteDispute_recyclerViewWordItemData {
    public String date, name;

    // 아이템 데이터 초기화
    public CommuteDispute_recyclerViewWordItemData(String date, String name) {
        this.date = date;
        this.name = name;
    }

    // 입력받은 숫자의 리스트 생성
    public static ArrayList<CommuteDispute_recyclerViewWordItemData> createContactsList(int numContacts) {
        ArrayList<CommuteDispute_recyclerViewWordItemData> contacts = new ArrayList<CommuteDispute_recyclerViewWordItemData>();

        for (int i = 1; i <= numContacts; i++) {
            // 공지 사항 아이템 데이터 생성 및 초기화, 이 부분은 임시로 고정된 값을 사용하지만
            // 실제로는 DB에서 데이터를 받아와서 아이템을 생성하는 용도로 사용될 수 있습니다.
            contacts.add(new CommuteDispute_recyclerViewWordItemData("이의신청 날짜", "직원이름")); // DB에서 받아서 제목 넣기
        }

        return contacts;
    }
}