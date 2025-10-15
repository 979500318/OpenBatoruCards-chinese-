package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;

public final class LRIGA_R2_AzaelaFlamingReversal extends Card {

    public LRIGA_R2_AzaelaFlamingReversal()
    {
        setImageSets("WXDi-P16-039");

        setOriginalName("アザエラ「逆転の炎」");
        setAltNames("アザエラギャクテンノホノオ Azaera Gyakuten no Honoo");
        setDescription("jp",
                "@E：対戦相手のパワー10000以下のシグニ１体を対象とし、それをバニッシュする。\n" +
                "@E：次の対戦相手のターン終了時まで、このルリグは@>@U $T2：あなたか対戦相手のライフクロス１枚がクラッシュされたとき、カードを１枚引くか【エナチャージ１】をする。@@を得る。"
        );

        setName("en", "Azaela \"Flames of Reversal\"");
        setDescription("en",
                "@E: Vanish target SIGNI on your opponent's field with power 10000 or less.\n@E: This LRIG gains@>@U $T2: Whenever one of your Life Cloth or your opponent's Life Cloth is crushed, draw a card or [[Ener Charge 1]].@@until the end of your opponent's next end phase."
        );
        
        setName("en_fan", "Azaela [Flaming Reversal]");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's SIGNI power 10000 or less, and banish it.\n" +
                "@E: Until the end of your opponent's next turn, this LRIG gains:" +
                "@>@U $T2: When 1 of your or your opponent's life cloth is crushed, draw 1 card or [[Ener Charge 1]]."
        );

		setName("zh_simplified", "阿左伊来「逆转的炎」");
        setDescription("zh_simplified", 
                "@E :对战对手的力量10000以下的精灵1只作为对象，将其破坏。\n" +
                "@E :直到下一个对战对手的回合结束时为止，这只分身得到\n" +
                "@>@U $T2 :当你或对战对手的生命护甲1张被击溃时，抽1张牌或[[能量填充1]]。@@\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.AZAELA);
        setLRIGTeam(CardLRIGTeam.MUGEN_SHOUJO);
        setColor(CardColor.RED);
        setCost(Cost.colorless(1));
        setLevel(2);
        setLimit(+1);
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

            registerEnterAbility(this::onEnterEff1);
            registerEnterAbility(this::onEnterEff2);
        }

        private void onEnterEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,10000)).get();
            banish(target);
        }
        private void onEnterEff2()
        {
            AutoAbility attachedAuto = new AutoAbility(GameEventId.CRUSH, this::onAttachedAutoEff);
            attachedAuto.setUseLimit(UseLimit.TURN, 2);
            
            attachAbility(getCardIndex(), attachedAuto, ChronoDuration.nextTurnEnd(getOpponent()));
        }
        private void onAttachedAutoEff(CardIndex caller)
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
