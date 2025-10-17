package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_B2_LuShangAzureGeneral extends Card {

    public SIGNI_B2_LuShangAzureGeneral()
    {
        setImageSets("WX25-P2-085", "SPDi45-03","SPDi45-03P");

        setOriginalName("蒼将　タイコウボウ");
        setAltNames("ソウショウタイコウボウ Soushou Taikoubou");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたの場に他の＜武勇＞のシグニがある場合、以下の２つから１つを選ぶ。\n" +
                "$$1対戦相手のシグニ１体を対象とし、それを凍結する。\n" +
                "$$2アップ状態のこのシグニをダウンしてもよい。そうした場合、対戦相手は手札を１枚捨てる。" +
                "~#：対戦相手のシグニ１体を対象とし、それをダウンし凍結する。カードを１枚引く。"
        );

        setName("en", "Lu Shang, Azure General");
        setDescription("en",
                "@U: At the beginning of your attack phase, if there is another <<Valor>> SIGNI on your field, @[@|choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's SIGNI, and freeze it.\n" +
                "$$2 You may down this SIGNI. If you do, your opponent discards 1 card from their hand." +
                "~#Target 1 of your opponent's SIGNI, and down and freeze it. Draw 1 card."
        );

		setName("zh_simplified", "苍将 太公望");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，你的场上有其他的<<武勇>>精灵的场合，从以下的2种选1种。\n" +
                "$$1 对战对手的精灵1只作为对象，将其冻结。\n" +
                "$$2 可以把竖直状态的这只精灵#D。这样做的场合，对战对手把手牌1张舍弃。" +
                "~#对战对手的精灵1只作为对象，将其#D并冻结。抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VALOR);
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

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.VALOR).except(getCardIndex()).getValidTargetsCount() > 0)
            {
                if(playerChoiceMode() == 1)
                {
                    CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FREEZE).OP().SIGNI()).get();
                    freeze(target);
                } else if(!isState(CardStateFlag.DOWNED) && playerChoiceActivate() && down()) {
                    discard(getOpponent(), 1);
                }
            }
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FREEZE).OP().SIGNI()).get();
            down(target);
            freeze(target);
            
            draw(1);
        }
    }
}
