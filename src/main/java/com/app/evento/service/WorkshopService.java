package com.app.evento.service;

import com.app.evento.models.Workshop;

import java.util.List;

public interface WorkshopService {
    Workshop addWorkshop(Workshop workshop);
    Workshop getWorkshop(String id) throws Exception;
    List<Workshop> getWorkshops();
    Workshop updateWorkShop(Workshop workshop,String id) throws Exception;
    void DeleteWorkshop(String id) throws Exception;
    boolean changeGuestPaymentStatus(String workShopId,String guestId,boolean pmtStatus) throws Exception;
    boolean changeGuestPresence(String workShopId,String guestId,boolean presence) throws Exception;
}
