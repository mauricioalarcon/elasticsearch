/*
 * Licensed to Elastic Search and Shay Banon under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. Elastic Search licenses this
 * file to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.elasticsearch.search;

import org.elasticsearch.common.io.stream.StreamInput;
import org.elasticsearch.common.io.stream.StreamOutput;
import org.elasticsearch.common.io.stream.Streamable;

import java.io.IOException;
import java.io.Serializable;

/**
 * The target that the search request was executed on.
 *
 * @author kimchy (shay.banon)
 */
public class SearchShardTarget implements Streamable, Serializable {

    private String nodeId;

    private String index;

    private int shardId;

    private SearchShardTarget() {

    }

    public SearchShardTarget(String nodeId, String index, int shardId) {
        this.nodeId = nodeId;
        this.index = index;
        this.shardId = shardId;
    }

    public String nodeId() {
        return nodeId;
    }

    public String getNodeId() {
        return nodeId;
    }

    public String index() {
        return index;
    }

    public String getIndex() {
        return index;
    }

    public int shardId() {
        return shardId;
    }

    public int getShardId() {
        return shardId;
    }

    public static SearchShardTarget readSearchShardTarget(StreamInput in) throws IOException {
        SearchShardTarget result = new SearchShardTarget();
        result.readFrom(in);
        return result;
    }

    @Override public void readFrom(StreamInput in) throws IOException {
        nodeId = in.readUTF();
        index = in.readUTF();
        shardId = in.readVInt();
    }

    @Override public void writeTo(StreamOutput out) throws IOException {
        out.writeUTF(nodeId);
        out.writeUTF(index);
        out.writeVInt(shardId);
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SearchShardTarget that = (SearchShardTarget) o;

        if (shardId != that.shardId) return false;
        if (index != null ? !index.equals(that.index) : that.index != null) return false;
        if (nodeId != null ? !nodeId.equals(that.nodeId) : that.nodeId != null) return false;

        return true;
    }

    @Override public int hashCode() {
        int result = nodeId != null ? nodeId.hashCode() : 0;
        result = 31 * result + (index != null ? index.hashCode() : 0);
        result = 31 * result + shardId;
        return result;
    }

    @Override public String toString() {
        return "[" + nodeId + "][" + index + "][" + shardId + "]";
    }
}
