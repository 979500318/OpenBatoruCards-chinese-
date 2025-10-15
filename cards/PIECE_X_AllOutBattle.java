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
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.cost.DownCost;

public final class PIECE_X_AllOutBattle extends Card {

    public PIECE_X_AllOutBattle()
    {
        setImageSets("WXDi-P15-004");

        setOriginalName("オールアウトバトル");
        setAltNames("Aaru Auto Batoru All Out Allout");
        setDescription("jp",
                "ターン終了時まで、あなたのすべてのルリグは以下の能力を得る。\n" +
                "@>@A $T1 #D：このルリグと共通する色を持つ対戦相手のシグニ１体を対象とし、それをバニッシュする。\n" +
                "@A $T1 #D：カードを１枚引くか【エナチャージ１】をする。"
        );

        setName("en", "All - Out Battle");
        setDescription("en",
                "All LRIG on your field gain the following abilities until end of turn.\n@>@A $T1 #D: Vanish target SIGNI on your opponent's field that shares a color with this LRIG.\n@A $T1 #D: Draw a card or [[Ener Charge 1]]."
        );
        
        setName("en_fan", "All-Out Battle");
        setDescription("en_fan",
                "Until end of turn, all of your LRIG gain:" +
                "@>@A $T1 #D: Target 1 of your opponent's SIGNI that shares a common color with this LRIG, and banish it.\n" +
                "@A $T1 #D: Draw 1 card or [[Ener Charge 1]]."
        );

		setName("zh_simplified", "全力战斗");
        setDescription("zh_simplified", 
                "直到回合结束时为止，你的全部的分身得到以下的能力。\n" +
                "@>@A $T1 #D:持有与这只分身共通颜色的对战对手的精灵1只作为对象，将其破坏。\n" +
                "@A $T1 #D抽1张牌或[[能量填充1]]。@@\n"
        );

        setType(CardType.PIECE);
        setCost(Cost.colorless(1));
        setUseTiming(UseTiming.MAIN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
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
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withColor(getAbility().getSourceCardIndex().getIndexedInstance().getColor())).get();
            banish(target);
        }
        private void onAttachedActionEff2()
        {
            if(playerChoiceAction(ActionHint.DRAW, ActionHint.ENER) == 1)
            {
                draw(1);
            } else {
                enerCharge(1);
            }
        }
    }
}
