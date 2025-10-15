package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.GameConst.CardUnderCategory;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_R3_ExMemoriaPhantomApparitionPrincess extends Card {

    public SIGNI_R3_ExMemoriaPhantomApparitionPrincess()
    {
        setImageSets("WXDi-P11-042", "WXDi-P11-042P");

        setOriginalName("幻怪姫　エクス//メモリア");
        setAltNames("ゲンカイキエクスメモリア Genkaiki Ekusu Memoria");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、対戦相手のパワー10000以下のシグニ１体を対象とし、このシグニの下から赤のシグニ１枚をトラッシュに置いてもよい。そうした場合、それをバニッシュする。\n" +
                "@U：あなたのメインフェイズ以外でこのシグニがバニッシュされたとき、このシグニの下にあったカード１枚につき対戦相手は自分のエナゾーンからカード１枚を選びトラッシュに置く。\n" +
                "@E：あなたのエナゾーンからシグニを３枚までこのシグニの下に置く。"
        );

        setName("en", "Ex//Memoria, Phantom Spirit Queen");
        setDescription("en",
                "@U: At the beginning of your attack phase, you may put a red SIGNI underneath this SIGNI into their owner's trash. If you do, vanish target SIGNI on your opponent's field with power 10000 or less.\n" +
                "@U: When this SIGNI is vanished outside of your main phase, your opponent chooses a card from their Ener Zone and puts it into their trash for each card that was underneath this SIGNI.\n" +
                "@E: Put up to three SIGNI from your Ener Zone under this SIGNI."
        );
        
        setName("en_fan", "Ex//Memoria, Phantom Apparition Princess");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, target 1 of your opponent's SIGNI with power 10000 or less, and you may put 1 red SIGNI from under this SIGNI into the trash. If you do, banish it.\n" +
                "@U: When this SIGNI is banished other than during your main phase, for each card that was under this SIGNI, your opponent chooses 1 card from their ener zone and puts it into the trash.\n" +
                "@E: Put up to 3 SIGNI from your ener zone under this SIGNI."
        );

		setName("zh_simplified", "幻怪姬 艾克斯//回忆");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，对战对手的力量10000以下的精灵1只作为对象，可以从这只精灵的下面把红色的精灵1张放置到废弃区。这样做的场合，将其破坏。\n" +
                "@U :当在你的主要阶段以外把这只精灵被破坏时，依据这只精灵的下面原有的牌的数量，每有1张对战对手就从自己的能量区选1张牌放置到废弃区。\n" +
                "@E :从你的能量区把精灵3张最多放置到这只精灵的下面。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.APPARITION);
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

            AutoAbility auto1 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff1);
            auto1.setCondition(this::onAutoEff1Cond);
            
            AutoAbility auto2 = registerAutoAbility(GameEventId.BANISH, this::onAutoEff2);
            auto2.setCondition(this::onAutoEff2Cond);
            
            registerEnterAbility(this::onEnterEff);
        }

        private ConditionState onAutoEff1Cond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0, 10000)).get();

            if(target != null)
            {
                CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.TRASH).own().SIGNI().withColor(CardColor.RED).under(getCardIndex())).get();
                
                if(trash(cardIndex))
                {
                    banish(target);
                }
            }
        }
        
        private int cacheUnderCount;
        private ConditionState onAutoEff2Cond()
        {
            if(!isOwnTurn() || getCurrentPhase() != GamePhase.MAIN)
            {
                cacheUnderCount = getCardsUnderCount(CardUnderCategory.UNDER);
                return ConditionState.OK;
            }
            return ConditionState.BAD;
        }
        private void onAutoEff2()
        {
            if(cacheUnderCount > 0)
            {
                DataTable<CardIndex> data = playerTargetCard(getOpponent(), Math.min(cacheUnderCount, getEnerCount(getOpponent())), new TargetFilter(TargetHint.BURN).own().fromEner());
                trash(data);
            }
        }
        
        private void onEnterEff()
        {
            DataTable<CardIndex> data = playerTargetCard(0,3, new TargetFilter(TargetHint.UNDER).own().SIGNI().fromEner());
            attach(getCardIndex(), data, CardUnderType.UNDER_GENERIC);
        }
    }
}
