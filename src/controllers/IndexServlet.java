package controllers;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Message;
import utils.DBUtil;
@WebServlet("/index")
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public IndexServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		   EntityManager em = DBUtil.createEntityManager();

	        List<Message> messages = em.createNamedQuery("getAllMessages", Message.class).getResultList();
	        response.getWriter().append(Integer.valueOf(messages.size()).toString());

	        em.close();
	        request.setAttribute("messages", messages);

	        // フラッシュメッセージがセッションスコープにセットされていたら
	        if(request.getSession().getAttribute("flush") != null) {
	            // セッションスコープ内のフラッシュメッセージをリクエストスコープに保存し、セッションスコープからは削除する
	            request.setAttribute("flush", request.getSession().getAttribute("flush"));
	            request.getSession().removeAttribute("flush");
	        }

	        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/messages/index.jsp");
	        rd.forward(request, response);
	    }
	}

