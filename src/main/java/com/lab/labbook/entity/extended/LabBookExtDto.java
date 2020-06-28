package com.lab.labbook.entity.extended;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class LabBookExtDto {
    private Long id;
    private String title;
    private String description;
    private String conclusion;
    private BigDecimal density;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private String status;
    private Long userId;
    private Long seriesId;

    public static class LabExtBuilder {
        private Long id;
        private String title;
        private String description;
        private String conclusion;
        private BigDecimal density;
        private LocalDateTime createdDate;
        private LocalDateTime updatedDate;
        private String status;
        private Long userId;
        private Long seriesId;

        public LabExtBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public LabExtBuilder title(String title) {
            this.title = title;
            return this;
        }

        public LabExtBuilder description(String description) {
            this.description = description;
            return this;
        }

        public LabExtBuilder conclusion(String conclusion) {
            this.conclusion = conclusion;
            return this;
        }

        public LabExtBuilder density(BigDecimal density) {
            this.density = density;
            return this;
        }

        public LabExtBuilder creationDate(LocalDateTime creationDate) {
            this.createdDate = creationDate;
            return this;
        }

        public LabExtBuilder updateDate(LocalDateTime updateDate) {
            this.updatedDate = updateDate;
            return this;
        }
        public LabExtBuilder status(String status) {
            this.status = status;
            return this;
        }

        public LabExtBuilder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public LabExtBuilder seriesId(Long seriesId) {
            this.seriesId = seriesId;
            return this;
        }

        public LabBookExtDto build() {
            return new LabBookExtDto(id, title, description, conclusion, density,
                    createdDate, updatedDate, status, userId, seriesId);
        }

    }

    private LabBookExtDto(Long id, String title, String description, String conclusion, BigDecimal density,
                          LocalDateTime createdDate, LocalDateTime updatedDate, String status, Long userId, Long seriesId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.conclusion = conclusion;
        this.density = density;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.status = status;
        this.userId = userId;
        this.seriesId = seriesId;
    }
}
