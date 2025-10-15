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
import open.batoru.data.ability.stock.StockAbilityCross.LeftOf;

public final class SIGNI_R1_USPSmallLeftGun extends Card {

    public SIGNI_R1_USPSmallLeftGun()
    {
        setImageSets("WX25-P1-072");

        setOriginalName("小左砲　ウスピー");
        setAltNames("ショウサホウウスピー Shousahou Usupii");
        setDescription("jp",
                "=X《爆右砲　セイデル》か《小右砲　エペナナ》の左\n" +
                "+U $T1：このシグニが#Hしたとき、対戦相手のパワー5000以下のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "USP, Small Left Gun");
        setDescription("en",
                "=X Left of \"Seidel, Explosive Right Gun\" or \"MP7, Small Right Gun\"\n" +
                "+U $T1: When this SIGNI reaches #H @[Heaven]@, target 1 of your opponent's SIGNI with power 5000 or less, and banish it."
        );

		setName("zh_simplified", "小左炮 USP自动装填手枪");
        setDescription("zh_simplified", 
                "[[交错]]《爆右砲　セイデル》或《小右砲エベナナ》的左侧（假如这个条件满足，那么这些处于交错状态）\n" +
                "[交错]@U $T1 :当这只精灵达成[天堂]时，对战对手的力量5000以下的精灵1只作为对象，将其破坏。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.WEAPON);
        setLevel(1);
        setPower(2000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerStockAbility(new StockAbilityCross(new LeftOf("爆右砲　セイデル", "小右砲　エペナナ")));
            
            AutoAbility auto = registerAutoAbility(GameEventId.HEAVEN, this::onAutoEff);
            auto.setUseLimit(UseLimit.TURN, 1);
        }
        
        private void onAutoEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,5000)).get();
            banish(target);
        }
    }
}
