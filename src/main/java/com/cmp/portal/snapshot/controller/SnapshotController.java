package com.cmp.portal.snapshot.controller;

import com.cmp.portal.common.ResponseData;
import com.cmp.portal.common.WebUtil;
import com.cmp.portal.snapshot.model.res.ResSnapshots;
import com.cmp.portal.snapshot.service.SnapshotService;
import com.cmp.portal.user.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("")
public class SnapshotController {

    private static final Logger logger = LoggerFactory.getLogger(SnapshotController.class);

    @Autowired
    private SnapshotService snapshotService;

    @RequestMapping("snapshotListPage")
    public ModelAndView snapshotListPage() {
        return new ModelAndView("pages/snapshot/snapshot-list.html");
    }

    @RequestMapping("snapshotDetailPage")
    public ModelAndView snapshotDetailPage() {
        return new ModelAndView("pages/snapshot/snapshot-detail.html");
    }

    /**
     * 查询快照列表
     *
     * @param cloudId 云id
     * @return 快照列表
     */
    @RequestMapping("/snapshots")
    @ResponseBody
    public ResponseData<ResSnapshots> describeSnapshots(@RequestParam(required = false) String cloudId) {
        try {
            User user = WebUtil.getCurrentUser();
            ResponseEntity<ResSnapshots> response = snapshotService.describeSnapshots(user, cloudId);
            return ResponseData.success(response.getBody());
        } catch (Exception e) {
            logger.info(e.getMessage());
            return ResponseData.failure(e.getMessage());
        }
    }
}
