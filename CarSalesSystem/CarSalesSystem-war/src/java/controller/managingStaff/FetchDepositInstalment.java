/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.managingStaff;

import static controller.managingStaff.FetchCustomerAccount.validateManagerSession;
import facade.DepositFacade;
import facade.InstalmentPlanFacade;
import facade.ManagingStaffFacade;
import jakarta.ejb.EJB;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import model.CarStock;
import model.Deposit;
import model.InstalmentPlan;
import model.Sales;
import model.Users;
import util.DepositInstalment;

/**
 *
 * @author Chew Jin Ni
 */
public class FetchDepositInstalment extends HttpServlet {

    @EJB
    private ManagingStaffFacade ManagingStaffFacade;
    @EJB
    private DepositFacade DepositFacade;
    @EJB
    private InstalmentPlanFacade InstalmentPlanFacade;
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (!validateManagerSession(request, response)) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            Users user = (Users) session.getAttribute("user");
            String manager_id = user.getUser_id();
            
            List<Object[]> fetchLists = ManagingStaffFacade.findRelatedDepositInstalment(manager_id, CarStock.StockStatus.Interested, Sales.SalesStatus.Cancelled, Sales.SalesStatus.Pending_Payment);
            List<DepositInstalment> deposit_instalment_list = new ArrayList<>();
            List<String> sales_id_list = new ArrayList<>();
            
            for(Object[] list : fetchLists){
                DepositInstalment depIns = new DepositInstalment();
                depIns.setSales_id((String) list[0]);
                depIns.setSalesman_id((String) list[1]);
                depIns.setCustomer_id((String) list[2]);
                depIns.setCar_id((String) list[3]);
                depIns.setModel_name((String) list[4]);
                depIns.setCar_price((double) list[5]);
                deposit_instalment_list.add(depIns);
            }
            
            List<Deposit> dp_list = DepositFacade.findAll();
            for(Deposit dp : dp_list){
                for(DepositInstalment depIns : deposit_instalment_list){
                    if(depIns.getSales_id().equals(dp.getSales_id().getSales_id())){
                        depIns.setCreatedDate(dp.getCreated_at());
                        depIns.setDeposit_id(dp.getDeposit_id());
                        depIns.setDeposit_amount(dp.getDeposit_amount());
                        depIns.setDeposit_doc(dp.getDeposit_doc());
                        if(dp.getRemark() != null){
                            depIns.setDeposit_remark(dp.getRemark());
                        }else{
                            depIns.setDeposit_remark(null);
                        }
                        depIns.setDeposit_status(dp.getStatus().toString());
                    }
                }
            }
            
            List<InstalmentPlan> instalment_plan_list = InstalmentPlanFacade.findAll();
            for(InstalmentPlan ip : instalment_plan_list){
                for(DepositInstalment depIns : deposit_instalment_list){
                    if(depIns.getSales_id().equals(ip.getSales_id().getSales_id())){
                        depIns.setInstalment_id(ip.getInstalment_id());
                        depIns.setInterest_rate(ip.getInterest_rate());
                        depIns.setTotal_amount(ip.getTotal_amount());
                        depIns.setMonthly_payment(ip.getMonthly_payment());
                        depIns.setTenure_months(ip.getTenure_months());
                        depIns.setPlan_status(ip.getPlan_status().toString());
                        if(ip.getRemark() != null){
                            depIns.setPlan_remark(ip.getRemark());
                        }else{
                            depIns.setPlan_remark(null);
                        }
                    }
                }
            }
            
            if(session.getAttribute("deposit_instalment_list") != null){
                session.removeAttribute("deposit_instalment_list");
                session.setAttribute("deposit_instalment_list", deposit_instalment_list);
            }else{
                session.setAttribute("deposit_instalment_list", deposit_instalment_list);
            }
                       
            response.sendRedirect(request.getContextPath() + "/managingStaff/deposit_instalment_approval.jsp");
        }catch(Exception e){
            System.out.println("Error in fetching deposit and instalment plan: " + e);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
