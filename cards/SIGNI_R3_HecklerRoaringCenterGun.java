package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityConst.ChoiceLogic;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.DownCost;
import open.batoru.data.ability.stock.StockAbilityCross;
import open.batoru.data.ability.stock.StockAbilityCross.LeftOf;
import open.batoru.data.ability.stock.StockAbilityCross.RightOf;

public final class SIGNI_R3_HecklerRoaringCenterGun extends Card {

    public SIGNI_R3_HecklerRoaringCenterGun()
    {
        setImageSets("WX25-P1-054", "WX25-P1-054U");
        setLinkedImageSets("WX25-P1-075","WX25-P1-076");

        setOriginalName("轟中砲　ヘッケラ");
        setAltNames("ゴウチュウホウヘッケラ Gouchuuhou Hekkera");
        setDescription("jp",
                "=X《爆左砲　コック》の右か《爆右砲　セイデル》の左\n" +
                "@A $T1 @[アップ状態の＜ウェポン＞のシグニ１体をダウンする]@：【エナチャージ１】\n" +
                "+U $T1：このシグニが#Hしたとき、あなたの場に《合炎奇炎　タマヨリヒメ之参》がいる場合、あなたのエナゾーンから＜ウェポン＞のシグニ２枚をトラッシュに置いてもよい。そうした場合、対戦相手にダメージを与える。"
        );

        setName("en", "Heckler, Roaring Center Gun");
        setDescription("en",
                "=X Right of \"Koch, Explosive Left Gun\" or left of \"Seidel, Explosive Right Gun\"\n" +
                "@A $T1 @[Down 1 of your upped <<Weapon>> SIGNI]@: [[Ener Charge 1]]\n" +
                "+U $T1: When this SIGNI reaches #H @[Heaven]@, if your LRIG is \"Three of Tamayorihime, Strangely United Flames\", you may put 2 <<Weapon>> SIGNI from your ener zone into the trash. If you do, damage your opponent."
        );

		setName("zh_simplified", "轰中炮 黑克勒");
        setDescription("zh_simplified", 
                "[[交错]]《爆左砲　コック》的右侧或《爆右砲　セイデル》的左侧\n" +
                "@A $T1 竖直状态的<<ウェポン>>精灵1只横置:[[能量填充1]]\n" +
                "[交错]@U $T1 当这只精灵达成[天堂]时，你的场上有《合炎奇炎:玉依姬之参》的场合，可以从你的能量区把<<ウェポン>>精灵2张放置到废弃区。这样做的场合，给予对战对手伤害。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.WEAPON);
        setLevel(3);
        setPower(10000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerStockAbility(new StockAbilityCross(new RightOf("爆左砲　コック"), new LeftOf("爆右砲　セイデル")));

            ActionAbility act = registerActionAbility(new DownCost(new TargetFilter().SIGNI().withClass(CardSIGNIClass.WEAPON)), this::onActionEff);
            act.setUseLimit(UseLimit.TURN, 1);

            AutoAbility auto = registerAutoAbility(GameEventId.HEAVEN, this::onAutoEff);
            auto.setUseLimit(UseLimit.TURN, 1);
        }
        
        private void onActionEff()
        {
            enerCharge(1);
        }
        
        private void onAutoEff()
        {
            if(getLRIG(getOwner()).getIndexedInstance().getName().getValue().contains("合炎奇炎　タマヨリヒメ之参"))
            {
                DataTable<CardIndex> data = playerTargetCard(0,2, ChoiceLogic.BOOLEAN, new TargetFilter(TargetHint.TRASH).own().SIGNI().withClass(CardSIGNIClass.WEAPON).fromEner());
                
                if(trash(data) == 2)
                {
                    damage(getOpponent());
                }
            }
        }
    }
}
