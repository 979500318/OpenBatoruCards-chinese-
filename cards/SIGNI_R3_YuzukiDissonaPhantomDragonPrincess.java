package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_R3_YuzukiDissonaPhantomDragonPrincess extends Card {

    public SIGNI_R3_YuzukiDissonaPhantomDragonPrincess()
    {
        setImageSets("WXDi-P12-046", "WXDi-P12-046P");

        setOriginalName("幻竜姫　遊月//ディソナ");
        setAltNames("ゲンリュウキユヅキディソナ Genryuuki Yuzuki Disona");
        setDescription("jp",
                "@U $T1：このシグニが対戦相手のライフクロス１枚をクラッシュしたとき、対戦相手のパワー12000以下のシグニ１体を対象とし、あなたのエナゾーンから#Sのカード１枚をトラッシュに置いてもよい。そうした場合、それをバニッシュする。\n" +
                "@U：あなたのメインフェイズ以外でこのシグニがバニッシュされたとき、対戦相手は自分のエナゾーンからカード１枚を選びトラッシュに置く。"
        );

        setName("en", "Yuzuki//Dissona, Dragon Queen");
        setDescription("en",
                "@U $T1: When this SIGNI crushes one of your opponent's Life Cloth, you may put a #S card from your Ener Zone into your trash. If you do, vanish target SIGNI on your opponent's field with power 12000 or less.\n@U: When this SIGNI is vanished outside of your main phase, your opponent chooses a card from their Ener Zone and puts it into their trash."
        );
        
        setName("en_fan", "Yuzuki//Dissona, Phantom Dragon Princess");
        setDescription("en_fan",
                "@U $T1: When this SIGNI crushes 1 of your opponent's life cloth, target 1 of your opponent's SIGNI with power 12000 or less, and you may put 1 #S @[Dissona]@ card from your ener zone into the trash. If you do, banish it.\n" +
                "@U: When this SIGNI is banished other than during your main phase, your opponent chooses 1 card from their ener zone, and puts it into the trash."
        );

		setName("zh_simplified", "幻龙姬 游月//失调");
        setDescription("zh_simplified", 
                "@U $T1 当这只精灵把对战对手的生命护甲1张击溃时，对战对手的力量12000以下的精灵1只作为对象，可以从你的能量区把#S的牌1张放置到废弃区。这样做的场合，将其破坏。\n" +
                "@U :当在你的主要阶段以外把这只精灵破坏时，对战对手从自己的能量区选1张牌放置到废弃区。\n"
        );

        setCardFlags(CardFlag.DISSONA);

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DRAGON_BEAST);
        setLevel(3);
        setPower(10000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto1 = registerAutoAbility(GameEventId.CRUSH, this::onAutoEff1);
            auto1.setCondition(this::onAutoEff1Cond);
            auto1.setUseLimit(UseLimit.TURN, 1);

            AutoAbility auto2 = registerAutoAbility(GameEventId.BANISH, this::onAutoEff2);
            auto2.setCondition(this::onAutoEff2Cond);
        }

        private ConditionState onAutoEff1Cond(CardIndex caller)
        {
            return !isOwnCard(caller) && getEvent().getSourceCardIndex() == getCardIndex() ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,12000)).get();
            
            if(target != null)
            {
                CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.TRASH).own().dissona().fromEner()).get();
                
                if(trash(cardIndex))
                {
                    banish(target);
                }
            }
        }

        private ConditionState onAutoEff2Cond()
        {
            return !isOwnTurn() || getCurrentPhase() != GamePhase.MAIN ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff2()
        {
            CardIndex cardIndex = playerTargetCard(getOpponent(), new TargetFilter(TargetHint.BURN).own().fromEner()).get();
            trash(cardIndex);
        }
    }
}
