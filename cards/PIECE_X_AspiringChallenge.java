package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.cost.DownCost;

public final class PIECE_X_AspiringChallenge extends Card {

    public PIECE_X_AspiringChallenge()
    {
        setImageSets("WX24-P4-038");

        setOriginalName("憧れへの挑戦");
        setAltNames("アコガレヘノチョウセン Akogareha no Chousen");
        setDescription("jp",
                "ターン終了時まで、あなたのすべてのルリグは以下の能力を得る。\n" +
                "@>@A $T1 #D：あなたのトラッシュからこのルリグと共通する色を持つシグニ１枚を対象とし、それを場に出す。\n" +
                "@A $T1 #D：このルリグと同じレベルの対戦相手のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Aspiring Challenge");
        setDescription("en",
                "Until end of turn, all of your LRIG gain:" +
                "@>@A $T1 #D: Target 1 SIGNI that shares a common color with this LRIG from your trash, and put it onto the field.\n" +
                "@A $T1 #D: Target 1 of your opponent's SIGNI with the same level as this LRIG, and banish it."
        );

		setName("zh_simplified", "向憧憬的挑战");
        setDescription("zh_simplified", 
                "直到回合结束时为止，你的全部的分身得到以下的能力。\n" +
                "@>@A $T1 #D:从你的废弃区把持有与这只分身共通颜色的精灵1张作为对象，将其出场。\n" +
                "@A $T1 #D与这只分身相同等级的对战对手的精灵1只作为对象，将其破坏。@@\n"
        );

        setType(CardType.PIECE);
        setCost(Cost.colorless(1));
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

            registerPieceAbility(this::onPieceEff);
        }

        private void onPieceEff()
        {
            forEachLRIGOnField(getOwner(), cardIndex -> {
                ActionAbility attachedAct1 = new ActionAbility(new DownCost(), this::onAttachedActionEff1);
                attachedAct1.setUseLimit(UseLimit.TURN, 1);
                attachAbility(cardIndex, attachedAct1, ChronoDuration.turnEnd());
                
                ActionAbility attachedAct2 = new ActionAbility(new DownCost(), this::onAttachedActionEff2);
                attachedAct2.setUseLimit(UseLimit.TURN, 1);
                attachedAct2.setNestedDescriptionOffset(1);
                attachAbility(cardIndex, attachedAct2, ChronoDuration.turnEnd());
            });
        }
        private void onAttachedActionEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FIELD).own().SIGNI().withColor(getAbility().getSourceCardIndex().getIndexedInstance().getColor()).fromTrash().playable()).get();
            putOnField(target);
        }
        private void onAttachedActionEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withLevel(getAbility().getSourceCardIndex().getIndexedInstance().getLevel().getValue())).get();
            banish(target);
        }
    }
}
