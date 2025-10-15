package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.cost.DiscardCost;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.stock.StockAbilityLancer;
import open.batoru.data.ability.stock.StockAbilitySLancer;

public final class SIGNI_G3_CodeLabyrinthTobira extends Card {

    public SIGNI_G3_CodeLabyrinthTobira()
    {
        setImageSets("WXDi-P16-051");
        setLinkedImageSets("WXDi-P16-012");

        setOriginalName("コードラビリンス　トビラ");
        setAltNames("コードラビリンストビラ Koodo Rabirinsu Tobira");
        setDescription("jp",
                "@A $T1 @[手札を２枚捨てる]@：このターン終了時、カードを２枚引くか【エナチャージ２】をする。\n" +
                "@A %G %X：あなたの場に《収斂せし扉　アト＝トレ》がいる場合、ターン終了時まで、このシグニは【ランサー】を得る。あなたのエナゾーンにカードが無い場合、代わりにターン終了時まで、このシグニは【Ｓランサー】を得る。" +
                "~#：どちらか１つを選ぶ。\n" +
                "$$1対戦相手のアップ状態のシグニ１体を対象とし、それをバニッシュする。\n" +
                "$$2【エナチャージ１】"
        );

        setName("en", "Tobira, Code: Labyrinth");
        setDescription("en",
                "@A $T1 @[Discard two cards]@: At the end of this turn, draw two cards or [[Ener Charge 2]].\n@A %G %X: If \"At =``Tre=, the Converged Gate\" is on your field, this SIGNI gains [[Lancer]] until end of turn. If there are no cards in your Ener Zone, it gains [[S Lancer]] until end of turn instead." +
                "~#Choose one -- \n$$1Vanish target upped SIGNI on your opponent's field. \n$$2[[Ener Charge 1]]."
        );
        
        setName("en_fan", "Code Labyrinth Tobira");
        setDescription("en_fan",
                "@A $T1 @[Discard 2 cards from your hand]@: At the end of this turn, draw 2 cards or [[Ener Charge 2]].\n" +
                "@A %G %X: If your LRIG is \"At-Tre, Door Converger\", until end of turn, this SIGNI gains [[Lancer]]. If there are no cards in your ener zone, until end of turn, it gains [[S Lancer]] instead." +
                "~#@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's upped SIGNI, and banish it.\n" +
                "$$2 [[Ener Charge 1]]"
        );

		setName("zh_simplified", "迷牢代号 窗扉");
        setDescription("zh_simplified", 
                "@A $T1 手牌2张舍弃:这个回合结束时，抽2张牌或[[能量填充2]]。\n" +
                "@A %G%X:你的场上有《収斂せし扉　アト＝トレ》的场合，直到回合结束时为止，这只精灵得到[[枪兵]]。你的能量区没有牌的场合，作为替代，直到回合结束时为止，这只精灵得到[[S枪兵]]。" +
                "~#以下选1种。\n" +
                "$$1 对战对手的竖直状态的精灵1只作为对象，将其破坏。\n" +
                "$$2 [[能量填充1]]\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.LABYRINTH);
        setLevel(3);
        setPower(12000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            ActionAbility act1 = registerActionAbility(new DiscardCost(2), this::onActionEff1);
            act1.setUseLimit(UseLimit.TURN, 1);

            ActionAbility act2 = registerActionAbility(new EnerCost(Cost.color(CardColor.GREEN, 1) + Cost.colorless(1)), this::onActionEff2);
            act2.setCondition(this::onActionEff2Cond);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private void onActionEff1()
        {
            callDelayedEffect(ChronoDuration.turnEnd(), () -> {
                if(playerChoiceAction(ActionHint.DRAW, ActionHint.ENER) == 1)
                {
                    draw(2);
                } else {
                    enerCharge(2);
                }
            });
        }

        private ConditionState onActionEff2Cond()
        {
            return !getLRIG(getOwner()).getIndexedInstance().getName().getValue().contains("収斂せし扉　アト＝トレ") ||
                    getCardIndex().getIndexedInstance().getAbilityList().stream().anyMatch(ability ->
                     ability.getSourceStockAbility() instanceof StockAbilityLancer ||
                     ability.getSourceStockAbility() instanceof StockAbilitySLancer) ? ConditionState.WARN : ConditionState.OK;
        }
        private void onActionEff2()
        {
            if(getLRIG(getOwner()).getIndexedInstance().getName().getValue().contains("収斂せし扉　アト＝トレ"))
            {
                attachAbility(getCardIndex(), getEnerCount(getOwner()) > 0 ? new StockAbilityLancer() : new StockAbilitySLancer(), ChronoDuration.turnEnd());
            }
        }

        private void onLifeBurstEff()
        {
            if(playerChoiceMode() == 1)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().upped()).get();
                banish(target);
            } else {
                enerCharge(1);
            }
        }
    }
}

