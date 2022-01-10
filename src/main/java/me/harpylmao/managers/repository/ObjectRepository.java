package me.harpylmao.managers.repository;

import me.harpylmao.managers.model.Model;

/**
 * Created by HarpyLMAO
 * at 09/10/2021 19:47
 * all credits reserved
 */

public interface ObjectRepository<O extends Model> {
  O find(String id);
  void delete(String id);
  void save(O model);
}
