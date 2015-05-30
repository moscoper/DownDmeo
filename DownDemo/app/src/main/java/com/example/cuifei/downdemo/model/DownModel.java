package com.example.cuifei.downdemo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by cuifei on 15/5/30.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DownModel {

    List<Music> music;
    int count;

    public List<Music> getMusic() {
        return music;
    }

    public void setMusic(List<Music> music) {
        this.music = music;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
   public static class Music{
        private int id;

        private String music;

        private String name;

        private int orderBy;

        private int status;

        private String createTime;

        private int downStatu = -1;
        private int progress;


        public int getProgress() {
            return progress;
        }

        public void setProgress(int progress) {
            this.progress = progress;
        }

        public int getDownStatu() {
            return downStatu;
        }

        public void setDownStatu(int downStatu) {
            this.downStatu = downStatu;
        }

        public void setId(int id){
            this.id = id;
        }
        public int getId(){
            return this.id;
        }
        public void setMusic(String music){
            this.music = music;
        }
        public String getMusic(){
            return this.music;
        }
        public void setName(String name){
            this.name = name;
        }
        public String getName(){
            return this.name;
        }
        public void setOrderBy(int orderBy){
            this.orderBy = orderBy;
        }
        public int getOrderBy(){
            return this.orderBy;
        }
        public void setStatus(int status){
            this.status = status;
        }
        public int getStatus(){
            return this.status;
        }
        public void setCreateTime(String createTime){
            this.createTime = createTime;
        }
        public String getCreateTime(){
            return this.createTime;
        }
    }
}
