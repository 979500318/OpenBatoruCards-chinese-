package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.ConstantAbilityShared;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.modifiers.PowerModifier;
import open.batoru.data.ability.stock.StockAbilityShadow;

public final class LRIGA_G2_AnneColoring extends Card {

    public LRIGA_G2_AnneColoring()
    {
        setImageSets("WXDi-P11-038");

        setOriginalName("アン － 彩リ");
        setAltNames("アンドリ An Dori");
        setDescription("jp",
                "@E：あなたのターンの場合、次の対戦相手のターン終了時まで、このルリグは@>@C：あなたのシグニのパワーを＋10000する。@@を得る。\n" +
                "@E：対戦相手のターンの場合、ターン終了時まで、このルリグは@>@C：あなたのシグニは[[シャドウ]]を得る。@@を得る。"
        );

        setName("en", "Ann - Colorize");
        setDescription("en",
                "@E: If it's your turn, this LRIG gains@>@C: SIGNI on your field get +10000 power.@@until the end of your opponent's next end phase.\n" +
                "@E: If it's your opponent's turn, this LRIG gains@>@C: SIGNI on your field gain [[Shadow]].@@until end of turn."
        );
        
        setName("en_fan", "Anne Coloring");
        setDescription("en_fan",
                "@E: If it is your turn, until the end of your opponent's next turn, this LRIG gains:" +
                "@>@C: All of your SIGNI get +10000 power.@@" +
                "@E: If it is your opponent's turn, until end of turn, this LRIG gains:" +
                "@>@C: All of your SIGNI gain [[Shadow]]."
        );

		setName("zh_simplified", "安 - 彩染");
        setDescription("zh_simplified", 
                "@E :你的回合的场合，直到下一个对战对手的回合结束时为止，这只分身得到\n" +
                "@>@C :你的精灵的力量+10000。@@\n" +
                "@E :对战对手的回合的场合，直到回合结束时为止，这只分身得到\n" +
                "@>@C :你的精灵得到[[暗影]]。@@\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.ANN);
        setColor(CardColor.GREEN);
        setCost(Cost.colorless(2));
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(this::onEnterEff1);
            registerEnterAbility(this::onEnterEff2);
        }

        private void onEnterEff1()
        {
            if(isOwnTurn())
            {
                ConstantAbility attachedConst = new ConstantAbilityShared(new TargetFilter().own().SIGNI(), new PowerModifier(10000));
                attachAbility(getCardIndex(), attachedConst, ChronoDuration.nextTurnEnd(getOpponent()));
            }
        }
        
        private void onEnterEff2()
        {
            if(!isOwnTurn())
            {
                ConstantAbility attachedConst = new ConstantAbilityShared(new TargetFilter().own().SIGNI(), new AbilityGainModifier(this::onAttachedConstEff2ModGetSample));
                attachAbility(getCardIndex(), attachedConst, ChronoDuration.turnEnd());
            }
        }
        private Ability onAttachedConstEff2ModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerStockAbility(new StockAbilityShadow());
        }
    }
}
