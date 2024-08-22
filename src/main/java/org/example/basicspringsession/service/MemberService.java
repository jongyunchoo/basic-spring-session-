package org.example.basicspringsession.service;

import lombok.RequiredArgsConstructor;
import org.example.basicspringsession.dto.*;
import org.example.basicspringsession.entity.MemberEntity;
import org.example.basicspringsession.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public MemberSaveResponseDto saveMember(MemberSaveRequestDto requestDto) {
        MemberEntity newMember = new MemberEntity(requestDto.getName());
        MemberEntity savedMember = memberRepository.save(newMember);

        return new MemberSaveResponseDto(savedMember.getId(), savedMember.getName());
    }

    public List<MemberSimpleResponseDto> getMembers() {
        List<MemberEntity> memberList = memberRepository.findAll();

        List<MemberSimpleResponseDto> dtoList = new ArrayList<>();
        for(MemberEntity member : memberList) {
            MemberSimpleResponseDto dto = new MemberSimpleResponseDto(member.getName());
            dtoList.add(dto);
        }

        return dtoList;
    }

    public MemberDetailResponseDto getMember(Long memberId) {
        MemberEntity member = memberRepository.findById(memberId).orElseThrow(() -> new NullPointerException("멤버가 없습니다."));

        return new MemberDetailResponseDto(member.getId(), member.getName());
    }

    @Transactional
    public MemberUpdateResponseDto updateMember(Long memberId, MemberUpdateRequestDto requestDto) {
        MemberEntity member = memberRepository.findById(memberId).orElseThrow(() -> new NullPointerException("멤버가 없습니다."));

        member.update(requestDto.getName());

        return new MemberUpdateResponseDto(member.getId(), member.getName());
    }

    @Transactional
    public void deleteMember(Long memberId) {
    MemberEntity member = memberRepository.findById(memberId).orElseThrow(() -> new NullPointerException("멤버가 없습니다."));

    memberRepository.delete(member);
    }


}
