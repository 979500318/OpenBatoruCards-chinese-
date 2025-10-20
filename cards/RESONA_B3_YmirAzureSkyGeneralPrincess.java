package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.*;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;

public final class RESONA_B3_YmirAzureSkyGeneralPrincess extends Card {

    public RESONA_B3_YmirAzureSkyGeneralPrincess()
    {
        setImageSets("WX25-P2-TK06");

        setOriginalName("蒼穹将姫　ユミル");
        setAltNames("ソウキュウショウキユミル Soukyuu Shouki Yumiru");
        setDescription("jp",
                "手札とエナゾーンからシグニを合計３枚トラッシュに置く\n\n" +
                "@U：あなたのアタックフェイズ開始時、対戦相手の凍結状態のすべてのシグニをダウンし、対戦相手の他のすべてのシグニを凍結する。\n" +
                "@E：対戦相手のルリグ１体を対象とし、それを凍結する。"
        );

        setName("en", "Ymir, Azure Sky General Princess");
        setDescription("en",
                "Put 3 SIGNI from your hand and/or ener zone into the trash\n\n" +
                "@U: At the beginning of your attack phase, down all of your opponent's frozen SIGNI, and freeze all of your opponent's other SIGNI.\n" +
                "@E: Target 1 of your opponent's LRIG, and freeze it."
        );

		setName("zh_simplified", "苍穹将姬 尤弥尔");
        setDescription("zh_simplified", 
                "[[出现条件]]主要阶段从手牌和能量区把精灵合计3张放置到废弃区\n" +
                "@U :你的攻击阶段开始时，对战对手的冻结状态的全部的精灵横置，对战对手的其他的全部的精灵冻结。\n" +
                "@E :对战对手的分身1只作为对象，将其冻结。\n"
        );

        setCardFlags(CardFlag.CRAFT);

        setType(CardType.RESONA);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VALOR);
        setLevel(3);
        setPower(12000);
        setUseTiming(UseTiming.MAIN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            setUseCondition(UseCondition.RESONA, 3, new TargetFilter().or(new TargetFilter().fromHand(), new TargetFilter().fromEner()));
            
            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            registerEnterAbility(this::onEnterEff);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            down(new TargetFilter().OP().SIGNI().withState(CardStateFlag.FROZEN).getExportedData());
            freeze(new TargetFilter().OP().SIGNI().not(new TargetFilter().withState(CardStateFlag.FROZEN)).getExportedData());
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FREEZE).OP().anyLRIG()).get();
            freeze(target);
        }
    }
}

