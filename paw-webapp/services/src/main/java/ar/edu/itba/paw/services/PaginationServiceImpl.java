package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.Content;
import ar.edu.itba.paw.models.Review;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaginationServiceImpl implements PaginationService{

    @Override
    public int amountOfContentPages(int contentListSize,final int pageSize) {
        return (int)Math.ceil((double) contentListSize/(double)pageSize);
    }

    @Override
    public <T> List<T> pagePagination(List<T> list, int page,final int pageSize) {
        if(list == null || page*pageSize > list.size() + (pageSize - list.size()%pageSize) )        //Si se pasa mas de una pagina de lo disponible retorna null
            return null;
        if(list.size() < page*pageSize) {       //Si no llega a completar la pagina entera, que sirva los que pueda
            return list.subList((page-1)*pageSize,list.size());
        } else {
            return list.subList((page - 1) * pageSize, (page) * pageSize);
        }
    }

    @Override
    public <T> List<T> infiniteScrollPagination(List<T> list, int page,final int pageSize) {
        if(list.size() >= page*pageSize) {
            return list.subList(0, page * pageSize);
        } else {
            return list.subList(0, list.size());
        }
    }

    @Override
    public boolean checkPagination(int listSize, int page,final int pageSize) {
        if(page==1 && listSize==0)
            return false;
        return (page-1)*pageSize >= listSize;
    }

}
