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

public final class SIGNI_R2_KochExplosiveLeftGun extends Card {

    public SIGNI_R2_KochExplosiveLeftGun()
    {
        setImageSets("WX25-P1-075");
        setLinkedImageSets("WX25-P1-073");

        setOriginalName("爆左砲　コック");
        setAltNames("バクサホウコック Bakusahou Kokku");
        setDescription("jp",
                "=X《轟中砲　ヘッケラ》か《小右砲　エペナナ》の左\n" +
                "+U $T1：このシグニが#Hしたとき、対戦相手のパワー10000以下のシグニ１体を対象とし、あなたのエナゾーンから＜ウェポン＞のシグニ1枚をトラッシュに置いてもよい。そうした場合、それをバニッシュする。"
        );

        setName("en", "Koch, Explosive Left Gun");
        setDescription("en",
                "=X Left of \"Heckler, Roaring Center Gun\" or \"MP7, Small Right Gun\"\n" +
                "+U $T1: When this SIGNI reaches #H @[Heaven]@, target 1 of your opponent's SIGNI with power 10000 or less, and you may put 1 <<Weapon>> SIGNI from your ener zone into the trash. If you do, banish it."
        );

		setName("zh_simplified", "爆左炮 科赫");
        setDescription("zh_simplified", 
                "[[交错]]《轟中砲　ヘッケラ》或《小右砲エベナナ》的左侧（假如这个条件满足，那么这些处于交错状态）\n" +
                "[交错]@U $T1 :当这只精灵达成[天堂]时，对战对手的力量10000以下的精灵1只作为对象，可以从你的能量区把<<ウェポン>>精灵1张放置到废弃区。这样做的场合，将其破坏。（当交错状态的精灵攻击时，假如攻击中的精灵和与之交错的精灵都处于横置状态，那么这些都达成[天堂]）\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.WEAPON);
        setLevel(2);
        setPower(5000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerStockAbility(new StockAbilityCross(new LeftOf("轟中砲　ヘッケラ", "小右砲　エペナナ")));

            AutoAbility auto = registerAutoAbility(GameEventId.HEAVEN, this::onAutoEff);
            auto.setUseLimit(UseLimit.TURN, 1);
        }

        private void onAutoEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,10000)).get();
            
            if(target != null)
            {
                CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.TRASH).own().SIGNI().withClass(CardSIGNIClass.WEAPON).fromEner()).get();
                
                if(trash(cardIndex))
                {
                    banish(target);
                }
            }
        }
    }
}
