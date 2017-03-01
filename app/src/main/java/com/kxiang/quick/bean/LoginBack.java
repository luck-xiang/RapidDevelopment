package com.kxiang.quick.bean;

import java.util.List;

/**
 * 项目名称:IQ
 * 创建人:kexiang
 * 创建时间:2016/11/16 13:25
 */

public class LoginBack {


    /**
     * result : ok
     * relationshipShops : [{"shopName":"大川西坝子","id":"75b9a71b-611e-4091-b49d-9ef8b717fbff"}]
     * login_user_token : 820b6148-4503-4107-82ef-4da069412722
     * currentShop : {"id":"75b9a71b-611e-4091-b49d-9ef8b717fbff","name":"大川西坝子","parentId":null,"parentIds":null,"available":true,"symbol":"255f6cca-aff4-4404-bb6e-27a709a02fd7","fullname":"大川西坝子","address":"天宇路一号","thecontact":"18281506991","mchmobile":"18281506991","mchphone":"028-56987451","createtime":"2016-11-14 13:24:13","blurl":null,"shoppicture":"","shopdescribe":"","delivery":null,"shopstatus":"open","longitude":"104.01355","latitude":"30.654737","shopstyle":"green","shoppics":" ","wechat":"yqsh2016","alipay":"alipay_ID","bankNumber":"6221 8888888888888888","bankName":"中国银行(天宇路支付)","depositPrice":0,"hallprice":0,"depositnote":"","isautoconform":false,"picnail":""}
     * user : {"id":"44ec201f-586f-c43a-8a15-3a54400d0879","shopId":null,"username":"13881105163","roleIds":null,"aliasname":null,"mobile":"13881105163","createtime":"2016-11-23 13:32:25","type":"shop","viplevelid":1,"referrer":null,"roleNames":null,"headPortrait":null}
     * currentShopRoles : [{"id":"5","role":"shop_manager","description":"管理员","resourceIds":"1000,1001,1002,1003,1004","available":true,"type":"shop","customerid":null,"resourceIdsAsArray":["1000","1001","1002","1003","1004"]}]
     */

    private String result;
    private String login_user_token;
    private CurrentShopBean currentShop;
    private UserBean user;
    private List<RelationshipShopsBean> relationshipShops;
    private List<CurrentShopRolesBean> currentShopRoles;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getLogin_user_token() {
        return login_user_token;
    }

    public void setLogin_user_token(String login_user_token) {
        this.login_user_token = login_user_token;
    }

    public CurrentShopBean getCurrentShop() {
        return currentShop;
    }

    public void setCurrentShop(CurrentShopBean currentShop) {
        this.currentShop = currentShop;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public List<RelationshipShopsBean> getRelationshipShops() {
        return relationshipShops;
    }

    public void setRelationshipShops(List<RelationshipShopsBean> relationshipShops) {
        this.relationshipShops = relationshipShops;
    }

    public List<CurrentShopRolesBean> getCurrentShopRoles() {
        return currentShopRoles;
    }

    public void setCurrentShopRoles(List<CurrentShopRolesBean> currentShopRoles) {
        this.currentShopRoles = currentShopRoles;
    }

    public static class CurrentShopBean {
        /**
         * id : 75b9a71b-611e-4091-b49d-9ef8b717fbff
         * name : 大川西坝子
         * parentId : null
         * parentIds : null
         * available : true
         * symbol : 255f6cca-aff4-4404-bb6e-27a709a02fd7
         * fullname : 大川西坝子
         * address : 天宇路一号
         * thecontact : 18281506991
         * mchmobile : 18281506991
         * mchphone : 028-56987451
         * createtime : 2016-11-14 13:24:13
         * blurl : null
         * shoppicture :
         * shopdescribe :
         * delivery : null
         * shopstatus : open
         * longitude : 104.01355
         * latitude : 30.654737
         * shopstyle : green
         * shoppics :
         * wechat : yqsh2016
         * alipay : alipay_ID
         * bankNumber : 6221 8888888888888888
         * bankName : 中国银行(天宇路支付)
         * depositPrice : 0.0
         * hallprice : 0.0
         * depositnote :
         * isautoconform : false
         * picnail :
         */

        private String id;
        private String name;
        private Object parentId;
        private Object parentIds;
        private boolean available;
        private String symbol;
        private String fullname;
        private String address;
        private String thecontact;
        private String mchmobile;
        private String mchphone;
        private String createtime;
        private Object blurl;
        private String shoppicture;
        private String shopdescribe;
        private Object delivery;
        private String shopstatus;
        private String longitude;
        private String latitude;
        private String shopstyle;
        private String shoppics;
        private String wechat;
        private String alipay;
        private String bankNumber;
        private String bankName;
        private double depositPrice;
        private double hallprice;
        private String depositnote;
        private boolean isautoconform;
        private String picnail;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getParentId() {
            return parentId;
        }

        public void setParentId(Object parentId) {
            this.parentId = parentId;
        }

        public Object getParentIds() {
            return parentIds;
        }

        public void setParentIds(Object parentIds) {
            this.parentIds = parentIds;
        }

        public boolean isAvailable() {
            return available;
        }

        public void setAvailable(boolean available) {
            this.available = available;
        }

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

        public String getFullname() {
            return fullname;
        }

        public void setFullname(String fullname) {
            this.fullname = fullname;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getThecontact() {
            return thecontact;
        }

        public void setThecontact(String thecontact) {
            this.thecontact = thecontact;
        }

        public String getMchmobile() {
            return mchmobile;
        }

        public void setMchmobile(String mchmobile) {
            this.mchmobile = mchmobile;
        }

        public String getMchphone() {
            return mchphone;
        }

        public void setMchphone(String mchphone) {
            this.mchphone = mchphone;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public Object getBlurl() {
            return blurl;
        }

        public void setBlurl(Object blurl) {
            this.blurl = blurl;
        }

        public String getShoppicture() {
            return shoppicture;
        }

        public void setShoppicture(String shoppicture) {
            this.shoppicture = shoppicture;
        }

        public String getShopdescribe() {
            return shopdescribe;
        }

        public void setShopdescribe(String shopdescribe) {
            this.shopdescribe = shopdescribe;
        }

        public Object getDelivery() {
            return delivery;
        }

        public void setDelivery(Object delivery) {
            this.delivery = delivery;
        }

        public String getShopstatus() {
            return shopstatus;
        }

        public void setShopstatus(String shopstatus) {
            this.shopstatus = shopstatus;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getShopstyle() {
            return shopstyle;
        }

        public void setShopstyle(String shopstyle) {
            this.shopstyle = shopstyle;
        }

        public String getShoppics() {
            return shoppics;
        }

        public void setShoppics(String shoppics) {
            this.shoppics = shoppics;
        }

        public String getWechat() {
            return wechat;
        }

        public void setWechat(String wechat) {
            this.wechat = wechat;
        }

        public String getAlipay() {
            return alipay;
        }

        public void setAlipay(String alipay) {
            this.alipay = alipay;
        }

        public String getBankNumber() {
            return bankNumber;
        }

        public void setBankNumber(String bankNumber) {
            this.bankNumber = bankNumber;
        }

        public String getBankName() {
            return bankName;
        }

        public void setBankName(String bankName) {
            this.bankName = bankName;
        }

        public double getDepositPrice() {
            return depositPrice;
        }

        public void setDepositPrice(double depositPrice) {
            this.depositPrice = depositPrice;
        }

        public double getHallprice() {
            return hallprice;
        }

        public void setHallprice(double hallprice) {
            this.hallprice = hallprice;
        }

        public String getDepositnote() {
            return depositnote;
        }

        public void setDepositnote(String depositnote) {
            this.depositnote = depositnote;
        }

        public boolean isIsautoconform() {
            return isautoconform;
        }

        public void setIsautoconform(boolean isautoconform) {
            this.isautoconform = isautoconform;
        }

        public String getPicnail() {
            return picnail;
        }

        public void setPicnail(String picnail) {
            this.picnail = picnail;
        }
    }

    public static class UserBean {
        /**
         * id : 44ec201f-586f-c43a-8a15-3a54400d0879
         * shopId : null
         * username : 13881105163
         * roleIds : null
         * aliasname : null
         * mobile : 13881105163
         * createtime : 2016-11-23 13:32:25
         * type : shop
         * viplevelid : 1
         * referrer : null
         * roleNames : null
         * headPortrait : null
         */

        private String id;
        private String shopId;
        private String username;
        private Object roleIds;
        private String aliasname;
        private String mobile;
        private String createtime;
        private String type;
        private int viplevelid;
        private Object referrer;
        private Object roleNames;
        private Object headPortrait;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getShopId() {
            return shopId;
        }

        public void setShopId(String shopId) {
            this.shopId = shopId;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public Object getRoleIds() {
            return roleIds;
        }

        public void setRoleIds(Object roleIds) {
            this.roleIds = roleIds;
        }

        public String getAliasname() {
            return aliasname;
        }

        public void setAliasname(String aliasname) {
            this.aliasname = aliasname;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getViplevelid() {
            return viplevelid;
        }

        public void setViplevelid(int viplevelid) {
            this.viplevelid = viplevelid;
        }

        public Object getReferrer() {
            return referrer;
        }

        public void setReferrer(Object referrer) {
            this.referrer = referrer;
        }

        public Object getRoleNames() {
            return roleNames;
        }

        public void setRoleNames(Object roleNames) {
            this.roleNames = roleNames;
        }

        public Object getHeadPortrait() {
            return headPortrait;
        }

        public void setHeadPortrait(Object headPortrait) {
            this.headPortrait = headPortrait;
        }
    }

    public static class RelationshipShopsBean {
        /**
         * shopName : 大川西坝子
         * id : 75b9a71b-611e-4091-b49d-9ef8b717fbff
         */

        private String shopName;
        private String id;

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    public static class CurrentShopRolesBean {
        /**
         * id : 5
         * role : shop_manager
         * description : 管理员
         * resourceIds : 1000,1001,1002,1003,1004
         * available : true
         * type : shop
         * customerid : null
         * resourceIdsAsArray : ["1000","1001","1002","1003","1004"]
         */

        private String id;
        private String role;
        private String description;
        private String resourceIds;
        private boolean available;
        private String type;
        private Object customerid;
        private List<String> resourceIdsAsArray;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getResourceIds() {
            return resourceIds;
        }

        public void setResourceIds(String resourceIds) {
            this.resourceIds = resourceIds;
        }

        public boolean isAvailable() {
            return available;
        }

        public void setAvailable(boolean available) {
            this.available = available;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Object getCustomerid() {
            return customerid;
        }

        public void setCustomerid(Object customerid) {
            this.customerid = customerid;
        }

        public List<String> getResourceIdsAsArray() {
            return resourceIdsAsArray;
        }

        public void setResourceIdsAsArray(List<String> resourceIdsAsArray) {
            this.resourceIdsAsArray = resourceIdsAsArray;
        }
    }
}
