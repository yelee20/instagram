package com.example.demo.src.test.entity;


import com.example.demo.common.entity.BaseEntity;
import com.example.demo.src.test.model.MemoDto;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false)
@Getter
@Entity
@Table(name = "MEMO_IMAGE")
public class MemoImage extends BaseEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memoId")
    private Memo memo;

    @Column(name = "imageUrl", nullable = false, updatable = false)
    private String url;

    @Column(name = "altText")
    private String altText;

    @Column(name = "type", nullable = false, updatable = false)
    private ImageType imageType;

    @Column(name = "length")
    private Long length;

    public enum ImageType {
        VIDEO, PHOTO
    }

    @Builder
    public MemoImage(Long id, Memo memo, String url, ImageType imageType, String altText, Long length) {
        this.id = id;
        this.memo = memo;
        this.url = url;
        this.imageType = imageType;
        this.altText = altText;
        this.length = length;
    }


}
