/**
 * Copyright [2014] [Sandy Guerrero Cajas]

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */

package com.easytask.dataBase;

import java.util.List;

/**
 * Created by sandy on 3/11/14.
 */
public interface CRUD<T> {

    public T insert(T object) throws Exception;

    public T read(int id) throws Exception;

    public boolean update(T object) throws Exception;

    public boolean delete(T object) throws Exception;

    public List<T> getAll() throws Exception;
}
