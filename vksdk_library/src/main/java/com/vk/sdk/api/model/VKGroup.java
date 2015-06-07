package com.vk.sdk.api.model;

import android.os.Parcel;

import java.util.Map;

/**
 * VKGroup (page, event)
 * Created by alex_xpert on 28.01.14.
 */
public class VKGroup extends VKApiModel {
    public long id; // идентификатор сообщества
    public String name; // название сообщества
    public String screen_name;    // короткий адрес сообщества, например, apiclub
    public int is_closed; // является ли сообщество закрытым. Возможные значения: 0 — открытое, 1 — закрытое, 2 — частное
    public boolean is_admin; // является ли текущий пользователь руководителем сообщества?
    public int admin_level; // полномочия текущего пользователя (если is_admin == true): 1 — модератор, 2 — редактор, 3 — администратор
    public boolean is_member; // является ли текущий пользователь участником сообщества?
    public String type; // тип сообщества: group — группа, page — публичная страница, event — мероприятие
    public String photo_50; // url фотографии сообщества с размером 50x50px
    public String photo_100; // url фотографии сообщества с размером 100х100px
    public String photo_200; // url фотографии сообщества в максимальном размере

    public String city; // идентификатор города, указанного в информации о сообществе. Возвращается id города для метода places.getCityById. Если город не указан, возвращается 0
    public int country; // идентификатор страны, указанной в информации о сообществе. Возвращается id страны для метода places.getCountryById. Если страна не указана, возвращается 0
    public Map<String, Object> place; // место, указанное в информации о сообществе
    public String description; // текст описания сообщества
    public String wiki_page; // название главной вики-страницы сообщества
    public int members_count; // количество участников сообщества
    public Map<String, Integer> counters; // возвращается объект counters, содержащий счётчики сообщества, может включать любой набор из следующих полей: photos, albums, audios, videos, topics, docs
    public long start_date; // возвращается только для встреч и содержит время начала встречи в формате unixtime
    public long end_date; // возвращается только для встреч и содержит время окончания встречи в формате unixtime
    public boolean can_post; // информация о том, может ли текущий пользователь оставлять записи на стене сообщества. Возвращается 1, если пользователь может написать на стене группы, 0 – если не может
    public boolean can_see_all_posts; // информация о том, разрешено видеть чужие записи на стене группы. Возвращаемые значения: 1 —разрешено, 0 — не разрешено
    public boolean can_upload_doc; // информация о том, может ли текущий пользователь загружать документы в группу. Возвращается 1, если пользователь может загружать документы, 0 – если не может.
    public boolean can_create_topic; // информация о том, может ли текущий пользователь создать тему обсуждения в группе, используя метод board.addTopic. Возвращается 1, если пользователь может создать обсуждение, 0 – если не может
    public String activity; // строка состояния публичной страницы. У групп возвращается строковое значение, открыта ли группа или нет, а у событий дата начала
    public String status; // статус сообщества. Возвращается строка, содержащая текст статуса, расположенного на странице сообщества под его названием
    public String contacts; // информация из блока контактов публичной страницы.
    public String links; // информация из блока ссылок сообщества.
    public long fixed_post; // идентификатор post_id закрепленного поста сообщества. Сам пост можно получить, используя wall.getById, передав в поле posts – {group_id}_{post_id}
    public boolean verified; // возвращает информацию о том, является ли сообщество верифицированным
    public String site; // адрес сайта из поля «веб-сайт» в описании сообщества

    public long invited_by; // идентификатор пользователя, который отправил приглашение

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}