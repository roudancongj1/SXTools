package com.sics.sxt.pojo.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sics.sxt.dao.GMapper;
import com.sics.sxt.dao.GroupCessionUploadMidMapper;
import com.sics.sxt.pojo.vo.R;
import com.sics.sxt.service.serv.CommonService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.sics.sxt.pojo.bo.DB.*;


/**
 * @Auth:GuangYun
 * */

@Getter
@Setter
@Slf4j
@RestController
@TableName("NEWPLANMAPPING")

public class PlanMappingNew implements Serializable,Cloneable {

    @TableField("ripreceptno")
    private String ripreceptNo;

    @TableField("recomcode")
    private String recomCode;

    @TableField("relacode")
    private String relaCode;

    @TableField("operator")
    private String operator;

    @TableField("prevalidate")
    private Date prevaliDate;

    @TableField("PREINVALIDATE")
    private Date preinvaliDate;

    @TableField(exist = false)
    private String leftSql = "";

    @TableField(exist = false)
    private String rightSql = "";



    @Override
    public PlanMappingNew clone() {
        try {
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return (PlanMappingNew) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    private final GMapper gMapper;
    private final CommonService commonService;
    private final GroupCessionUploadMidMapper groupCessionUploadMidMapper;

    public PlanMappingNew(GMapper gMapper,  CommonService commonService, GroupCessionUploadMidMapper groupCessionUploadMidMapper) {
        this.gMapper = gMapper;
        this.commonService = commonService;
        this.groupCessionUploadMidMapper = groupCessionUploadMidMapper;
    }

    public R c1(){
        HashMap<String, String> map = new HashMap<>();
        try {
            map.put("1","ok");
            commonService.dropTable(SICS_DATA,GROUP_CESSION_UPLOAD_ALL);
            gMapper.createCessionAllTable();
            gMapper.createCessionAllTableKey();
            map.put("2","ok");
            commonService.truncateTable(SICS_DATA,GROUP_CESSION_UPLOAD_MID);
            gMapper.insertCessionMidTable();
            map.put("3","ok");
            gMapper.changeSameTranNo();
            map.put("4","ok");
            commonService.truncateTable(SICS_DATA,GROUP_CESSION_CLAIM_MID);
            gMapper.insertClaimMidTable();
            map.put("5","ok");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("0", "error");
        }
        return R.ok().put(map);
    }

    public R bs(){
        HashMap<String, String> map = new HashMap<>();
        try {
            commonService.truncateTable(SICS_DATA,GROUP_CESSION_UPLOAD_MID);
            gMapper.insertCessionMidTableHis();
            map.put("1","ok");
            gMapper.changeSameTranNo();
            map.put("2","ok");
            commonService.truncateTable(SICS_DATA,GROUP_CESSION_CLAIM_MID);
            gMapper.insertClaimMidTableHis();
            map.put("3","ok");


        } catch (Exception e) {
            e.printStackTrace();
            map.put("0", "error");
        }
        return R.ok().put(map);
    }
    public R c00(){
        HashMap<String, String> map = new HashMap<>();
        try {
            commonService.dropTable(SICS_DATA,HIS_GRP_CESSION_UPLOAD_ALL);
            gMapper.createCessionAllTableHis();
            gMapper.createCessionAllTableHisKey();
            map.put("1","ok");


        } catch (Exception e) {
            e.printStackTrace();
            map.put("0", "error");
        }
        return R.ok().put(map);
    }

    @PostMapping("sc")
    public R sc(){
        HashMap<String, String> map = new HashMap<>();
        try {
            groupCessionUploadMidMapper.deleteClaim();
            map.put("1","ok");
            groupCessionUploadMidMapper.deleteCession();
            map.put("2","ok");

        } catch (Exception e) {
            e.printStackTrace();
            map.put("0", "error");
        }
        return R.ok().put(map);
    }

    public R c3(){
        HashMap<String, String> map = new HashMap<>();
        try {
            commonService.dropTable(SICSLIFE_CEDED,TEMPLATE_GCLAIM_PLACEMENT_ACI);
            groupCessionUploadMidMapper.createTempClaim();

        } catch (Exception e) {
            e.printStackTrace();
            map.put("0", "error");
        }
        return R.ok().put(map);
    }
    public R c4(){
        HashMap<String, String> map = new HashMap<>();
        try {
            commonService.dropTable(SICSLIFE_CEDED,TEMPLATE_GROUP_PLACEMENT_ACI);
            groupCessionUploadMidMapper.createTempCession();

        } catch (Exception e) {
            e.printStackTrace();
            map.put("0", "error");
        }
        return R.ok().put(map);
    }

    @PostMapping("t1")
    public R t1(){
        HashMap<String, String> map = new HashMap<>();
        try {
            int i = commonService.queryOwnerTableCount(REINSR, RIPRECEPT);
            map.put("1","ok");
            map.put("num", Integer.toString(i));


        } catch (Exception e) {
            e.printStackTrace();
            map.put("0", "error");
        }
        return R.ok().put(map);
    }

    @PostMapping("ss")
    public R ss(){
        HashMap<String, String> map = new HashMap<>();
        try {
            groupCessionUploadMidMapper.tempToSicsByPlacementClaim();
            map.put("1","ok");
            groupCessionUploadMidMapper.tempToSicsByRetroClaim();
            map.put("2","ok");
            groupCessionUploadMidMapper.tempToSicsByPlacementCessionLc();
            map.put("3","ok");
            groupCessionUploadMidMapper.tempToSicsByPlacementCession();
            map.put("4","ok");
            groupCessionUploadMidMapper.tempToSicsByRetroCessionLc();
            map.put("5","ok");
            groupCessionUploadMidMapper.tempToSicsByRetroCession();
            map.put("6","ok");
            groupCessionUploadMidMapper.tempToSicsByGiupDateRetentionCessionLc();
            map.put("7","ok");
            groupCessionUploadMidMapper.tempToSicsByGiupDateRetentionCession();
            map.put("8","ok");

        } catch (Exception e) {
            e.printStackTrace();
            map.put("0", "error");
        }
        return R.ok().put(map);
    }

    @PostMapping("db")
    public R db(@RequestBody Map<String,String> param){
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        String[] localDate = LocalDate.now().toString().split("-");
        String suffix = "_G" + localDate[1] + localDate[2];
        commonService.dropTable(SICSLIFE_CEDED1,"hagpIN_policy"+suffix);
        commonService.dropTable(SICSLIFE_CEDED1,"GsicsPL_policy"+suffix);
        commonService.dropTable(SICSLIFE_CEDED1,"GsicsPL_policy"+suffix+"_tmp");
        commonService.dropTable(SICSLIFE_CEDED1,"GPoliyPL_SICS_to_HA"+suffix);
        commonService.dropTable(SICSLIFE_CEDED1,"GPoliyPL_HA_to_SICS"+suffix);
        commonService.dropTable(SICSLIFE_CEDED1,"GPoliyPL_SICS_to_HA_val"+suffix);
        commonService.dropTable(SICSLIFE_CEDED1,"GPoliyPL_HA_to_SICS_null"+suffix);
        commonService.dropTable(SICSLIFE_CEDED1,"GPoliyPL_SICS_to_HA_null"+suffix);

        commonService.dropTable(SICSLIFE_CEDED1,"hagp_claim_inward"+suffix);
        commonService.dropTable(SICSLIFE_CEDED1,"GsicsPL_claim"+suffix);
        commonService.dropTable(SICSLIFE_CEDED1,"GclaimPL_SICS_to_HA"+suffix);
        commonService.dropTable(SICSLIFE_CEDED1,"GclaimPL_HA_to_SICS"+suffix);
        commonService.dropTable(SICSLIFE_CEDED1,"GclaimPL_SICS_to_HA_val"+suffix);
        commonService.dropTable(SICSLIFE_CEDED1,"GclaimPL_HA_to_SICS_null"+suffix);
        commonService.dropTable(SICSLIFE_CEDED1,"GclaimPL_SICS_to_HA_null"+suffix);

        try {
            if (StringUtils.hasText(param.get("table"))) {
                gMapper.compareCession(true,suffix,null,null);
                gMapper.compareClaim(true,suffix,null,null);
            } else{
                String[] dates = param.get("date").split("/");
                gMapper.compareCession(false,suffix,dates[0],dates[1]);
                gMapper.compareClaim(false,suffix,dates[0],dates[1]);
            }
            map.put("场景HA、SICS都有分出 Cession", SICSLIFE_CEDED1 + ".GPoliyPL_SICS_to_HA_val" + suffix + "  --" + commonService.queryOwnerTableCount(SICSLIFE_CEDED1, "GPoliyPL_SICS_to_HA_val" + suffix));
            map.put("HA有分出，SICS 无分出 Cession", SICSLIFE_CEDED1 + ".GPoliyPL_HA_to_SICS_null" + suffix + "  --" + groupCessionUploadMidMapper.queryOwnerTableCountNotHASL01Cession(SICSLIFE_CEDED1, "GPoliyPL_HA_to_SICS_null" + suffix));
            map.put("SICS 有分出,HA无分出 Cession", SICSLIFE_CEDED1 + ".GPoliyPL_SICS_to_HA_null" + suffix + "  --" + commonService.queryOwnerTableCount(SICSLIFE_CEDED1, "GPoliyPL_SICS_to_HA_null" + suffix));
            map.put("场景HA、SICS都有分出 Claim", SICSLIFE_CEDED1 + ".GclaimPL_SICS_to_HA_val" + suffix + "  --" + commonService.queryOwnerTableCount(SICSLIFE_CEDED1, "GclaimPL_SICS_to_HA_val" + suffix));
            map.put("HA有分出，SICS 无分出 Claim", SICSLIFE_CEDED1 + ".GclaimPL_HA_to_SICS_null" + suffix + "  --" + groupCessionUploadMidMapper.queryOwnerTableCountNotHASL01Claim(SICSLIFE_CEDED1, "GclaimPL_HA_to_SICS_null" + suffix));
            map.put("SICS 有分出,HA无分出 Claim", SICSLIFE_CEDED1 + ".GclaimPL_SICS_to_HA_null" + suffix + "  --" + commonService.queryOwnerTableCount(SICSLIFE_CEDED1, "GclaimPL_SICS_to_HA_null" + suffix));
        } catch (Exception e) {
            e.printStackTrace();
            map.put("0", "error");
        }
        return R.ok().put(map);
    }



}
