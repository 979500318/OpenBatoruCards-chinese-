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

public final class SIGNI_R2_SeidelExplosiveRightGun extends Card {

    public SIGNI_R2_SeidelExplosiveRightGun()
    {
        setImageSets("WX25-P1-076");
        setLinkedImageSets("WX25-P1-072");

        setOriginalName("爆右砲　セイデル");
        setAltNames("バクウホウセイデル Bakuuhou Seideru");
        setDescription("jp",
                "=X《轟中砲　ヘッケラ》か《小左砲　ウスピー》の右\n" +
                "+U $T1：このシグニが#Hしたとき、対戦相手のエナゾーンにカードが２枚以上ある場合、対戦相手は自分のエナゾーンからカード１枚を選びトラッシュに置く。" +
                "~#：対戦相手のパワー8000以下のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Seidel, Explosive Right Gun");
        setDescription("en",
                "=X Right of \"Heckler, Roaring Center Gun\" or \"USP, Small Left Gun\"\n" +
                "+U $T1: When this SIGNI reaches #H @[Heaven]@, if there are 2 or more cards in your opponent's ener zone, your opponent chooses 1 card from their ener zone, and puts it into the trash." +
                "~#Target 1 of your opponent's SIGNI with power 8000 or less, and banish it."
        );

		setName("zh_simplified", "爆右炮 HKP7自动装填手枪");
        setDescription("zh_simplified", 
                "[[交错]]《轟中砲　ヘッケラ》或《小左砲ウスビー》的右侧\n" +
                "[交错]@U $T1 :当这只精灵达成[天堂]时，对战对手的能量区的牌在2张以上的场合，对战对手从自己的能量区选1张牌放置到废弃区。" +
                "~#对战对手的力量8000以下的精灵1只作为对象，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.WEAPON);
        setLevel(2);
        setPower(8000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerStockAbility(new StockAbilityCross(new RightOf("轟中砲　ヘッケラ", "小左砲　ウスピー")));

            AutoAbility auto = registerAutoAbility(GameEventId.HEAVEN, this::onAutoEff);
            auto.setUseLimit(UseLimit.TURN, 1);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private void onAutoEff()
        {
            if(getEnerCount(getOpponent()) >= 2)
            {
                CardIndex cardIndex = playerTargetCard(getOpponent(), new TargetFilter(TargetHint.BURN).own().fromEner()).get();
                trash(cardIndex);
            }
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,8000)).get();
            banish(target);
        }
    }
}
