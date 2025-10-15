package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.stock.StockAbilityCross;
import open.batoru.data.ability.stock.StockAbilityCross.RightOf;

public final class SIGNI_R1_MP7SmallRightGun extends Card {

    public SIGNI_R1_MP7SmallRightGun()
    {
        setImageSets("WX25-P1-073");
        setLinkedImageSets("WX25-P1-072");

        setOriginalName("小右砲　エペナナ");
        setAltNames("ショウユウホウエペナナ Shouyuuhou Epenana");
        setDescription("jp",
                "=X《爆左砲　コック》か《小左砲　ウスピー》の右\n" +
                "+U $T1：このシグニが#Hしたとき、対戦相手のエナゾーンから対戦相手のセンタールリグと共通する色を持たないカード1枚を対象とし、それをトラッシュに置く。" +
                "~#： 手札を1枚捨て、カードを3枚引く。"
        );

        setName("en", "MP7, Small Right Gun");
        setDescription("en",
                "=X Right of \"Koch, Explosive Left Gun\" or \"USP, Small Left Gun\"\n" +
                "+U $T1: When this SIGNI reaches #H @[Heaven]@, target 1 card from your opponent's ener zone that doesn't share a common color with your opponent's center LRIG, and put it into the trash." +
                "~#Discard 1 card from your hand, and draw 3 cards."
        );

		setName("zh_simplified", "小右炮 MP7冲锋枪");
        setDescription("zh_simplified", 
                "[[交错]]《爆左砲　コック》或《小左砲ウスビー》的右侧\n" +
                "[交错]@U :当这只精灵达成[天堂]时，从对战对手的能量区把不持有与对战对手的核心分身共通颜色的牌1张作为对象，将其放置到废弃区。" +
                "~#手牌1张舍弃，抽3张牌。（即使没有手牌舍弃也能抽牌）\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.WEAPON);
        setLevel(1);
        setPower(3000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerStockAbility(new StockAbilityCross(new RightOf("爆左砲　コック", "小左砲　ウスピー")));

            AutoAbility auto = registerAutoAbility(GameEventId.HEAVEN, this::onAutoEff);
            auto.setUseLimit(UseLimit.TURN, 1);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private void onAutoEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BURN).OP().fromEner().not(new TargetFilter().withColor(getLRIG(getOpponent()).getIndexedInstance().getColor()))).get();
            trash(target);
        }
        
        private void onLifeBurstEff()
        {
            discard(1);
            draw(3);
        }
    }
}
