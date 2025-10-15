package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_W3_CrusaderChan extends Card {

    public SIGNI_W3_CrusaderChan()
    {
        setImageSets("WXDi-CP02-TK03B");

        setOriginalName("クルセイダーちゃん");
        setAltNames("クルセイダーチャン Kuruseidaa-chan");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、対戦相手のシグニ１体を対象とし、それをトラッシュに置く。\n" +
                "@U：このシグニがアタックしたとき、対戦相手のシグニ１体を対象とし、それをトラッシュに置く。\n" +
                "@U：対戦相手のターン終了時、このシグニをゲームから除外する。"
        );

        setName("en", "Crusadie");
        setDescription("en",
                "@U: At the beginning of your attack phase, put target SIGNI on your opponent's field into its owner's trash.\n@U: Whenever this SIGNI attacks, put target SIGNI on your opponent's field into its owner's trash.\n@U: At the end of your opponent's turn, remove this SIGNI from the game.\n\n"
        );
        
        setName("en_fan", "Crusader-chan");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, target 1 of your opponent's SIGNI, and put it into the trash.\n" +
                "@U: Whenever this SIGNI attacks, target 1 of your opponent's SIGNI, and put it into the trash.\n" +
                "@U: At the end of your opponent's turn, exclude this SIGNI from the game."
        );

		setName("zh_simplified", "十字军酱");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，对战对手的精灵1只作为对象，将其放置到废弃区。\n" +
                "@U :当这只精灵攻击时，对战对手的精灵1只作为对象，将其放置到废弃区。\n" +
                "@U :对战对手的回合结束时，这只精灵从游戏除外。\n"
        );

        setCardFlags(CardFlag.CRAFT);

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
        setLevel(3);
        setPower(15000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
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
            
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff2);

            AutoAbility auto3 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff3);
            auto3.setCondition(this::onAutoEff3Cond);
        }

        private ConditionState onAutoEff1Cond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            onAutoEff2();
        }
        
        private void onAutoEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.TRASH).OP().SIGNI()).get();
            trash(target);
        }

        private ConditionState onAutoEff3Cond()
        {
            return !isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff3(CardIndex caller)
        {
            exclude(getCardIndex());
        }
    }
}
