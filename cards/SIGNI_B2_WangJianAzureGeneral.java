package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.stock.StockAbilityAssassin;

public final class SIGNI_B2_WangJianAzureGeneral extends Card {

    public SIGNI_B2_WangJianAzureGeneral()
    {
        setImageSets("WX25-P2-084");

        setOriginalName("蒼将　オウキ");
        setAltNames("ソウショウオウキ Soushou Ouki");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、以下の２つから１つを選ぶ。\n" +
                "$$1あなたの場に他の＜武勇＞のシグニがある場合、対戦相手のシグニを２体まで対象とし、それらを凍結する。\n" +
                "$$2あなたのエナゾーンから＜武勇＞のシグニ１枚をトラッシュに置いてもよい。そうした場合、ターン終了時まで、このシグニは[[アサシン（凍結状態のパワー3000以下のシグニ）]]を得る。"
        );

        setName("en", "Wang Jian, Azure General");
        setDescription("en",
                "@U: At the beginning of your attack phase, @[@|choose 1 of the following:|@]@\n" +
                "$$1 If there is another <<Valor>> SIGNI on your field, target up to 2 of your opponent's SIGNI, and freeze them.\n" +
                "$$2 You may put 1 <<Valor>> SIGNI from your ener zone into the trash. If you do, until end of turn, this SIGNI gains [[Assassin (frozen SIGNI with power 3000 or less)]]."
        );

		setName("zh_simplified", "苍将 王龁");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，从以下的2种选1种。\n" +
                "$$1 你的场上有其他的<<武勇>>精灵的场合，对战对手的精灵2只最多作为对象，将这些冻结。\n" +
                "$$2 可以从你的能量区把<<武勇>>精灵1张放置到废弃区。这样做的场合，直到回合结束时为止，这只精灵得到[[暗杀（冻结状态的力量3000以下的精灵）]]。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VALOR);
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

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(playerChoiceMode() == 1)
            {
                if(new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.VALOR).except(getCardIndex()).getValidTargetsCount() > 0)
                {
                    DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.FREEZE).OP().SIGNI());
                    freeze(data);
                }
            } else {
                CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.TRASH).own().SIGNI().withClass(CardSIGNIClass.VALOR).fromEner()).get();
                
                if(trash(cardIndex))
                {
                    attachAbility(getCardIndex(), new StockAbilityAssassin(this::onAttachedStockEffAddCond), ChronoDuration.turnEnd());
                }
            }
        }
        private ConditionState onAttachedStockEffAddCond(CardIndex cardIndexOpposite)
        {
            return cardIndexOpposite.getIndexedInstance().isState(CardStateFlag.FROZEN) &&
                   cardIndexOpposite.getIndexedInstance().getPower().getValue() <= 3000 ? ConditionState.OK : ConditionState.BAD;
        }
    }
}
