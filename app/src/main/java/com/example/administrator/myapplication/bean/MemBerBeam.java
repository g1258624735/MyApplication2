package com.example.administrator.myapplication.bean;

/**
 * <Pre>
 *     版本更新
 * </Pre>
 *
 * @author 刘阳
 * @version 1.0
 *          <p/>
 *          Create by 2016/1/27 15:38
 *         ({"orderBranchInfoId":"4028835353ca6aae0153ca834a93003b",
 *         "memberList":
 *         [{"id":"4028835353ca6aae0153ca7adadf001e","userName":null,"pwd":"iwjrw665q32","mobilePhone":"13538951807"}],
 *         "disBranchInfoId":"4028835353ca6aae0153ca834a93003b","distributeMemberId":
 *         "4028835353ca6aae0153ca8349f8003a","distributeMemberpwd":"opou9auh01z","code":"1"})
 */
public class MemBerBeam {
    public String orderBranchInfoId;
    public String disBranchInfoId;
    public String distributeMemberId;
    public String distributeMemberpwd;
    public String code;

    public String getOrderBranchInfoId() {
        return orderBranchInfoId;
    }

    public void setOrderBranchInfoId(String orderBranchInfoId) {
        this.orderBranchInfoId = orderBranchInfoId;
    }

    public String getDisBranchInfoId() {
        return disBranchInfoId;
    }

    public void setDisBranchInfoId(String disBranchInfoId) {
        this.disBranchInfoId = disBranchInfoId;
    }

    public String getDistributeMemberId() {
        return distributeMemberId;
    }

    public void setDistributeMemberId(String distributeMemberId) {
        this.distributeMemberId = distributeMemberId;
    }

    public String getDistributeMemberpwd() {
        return distributeMemberpwd;
    }

    public void setDistributeMemberpwd(String distributeMemberpwd) {
        this.distributeMemberpwd = distributeMemberpwd;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "MemBerBeam{" +
                "orderBranchInfoId='" + orderBranchInfoId + '\'' +
                ", disBranchInfoId='" + disBranchInfoId + '\'' +
                ", distributeMemberId='" + distributeMemberId + '\'' +
                ", distributeMemberpwd='" + distributeMemberpwd + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
