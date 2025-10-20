package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.DownCost;

public final class SIGNI_G2_PendulumArtVerdantBeauty extends Card {

    public SIGNI_G2_PendulumArtVerdantBeauty()
    {
        setImageSets("WX24-P4-081");

        setOriginalName("翠美　ペンデュラムアート");
        setAltNames("スイビペンデュラムアート Suibi Penduramu Aato");
        setDescription("jp",
                "@U：対戦相手のターン終了時、【エナチャージ１】をする。\n" +
                "@A #D：あなたのシグニ１体を対象とし、次の対戦相手のターン終了時まで、それのパワーを＋3000する。" +
                "~#：対戦相手のパワー7000以上のシグニ１体を対象とし、%Xを支払ってもよい。そうした場合、それをバニッシュする。"
        );

        setName("en", "Pendulum Art, Verdant Beauty");
        setDescription("en",
                "@U: At the end of your opponent's turn, [[Ener Charge 1]].\n" +
                "@A #D: Target 1 of your SIGNI, and until the end of your opponent's next turn, it gets +3000 power." +
                "~#Target 1 of your opponent's SIGNI with power 7000 or more, and you may pay %X. If you do, banish it."
        );

		setName("zh_simplified", "翠美 钟摆画");
        setDescription("zh_simplified", 
                "@U :对战对手的回合结束时，[[能量填充1]]。\n" +
                "@A 横置:你的精灵1只作为对象，直到下一个对战对手的回合结束时为止，其的力量+3000。" +
                "~#对战对手的力量7000以上的精灵1只作为对象，可以支付%X。这样做的场合，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BEAUTIFUL_TECHNIQUE);
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
            
            registerActionAbility(new DownCost(), this::onActionEff);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onAutoEffCond()
        {
            return !isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            enerCharge(1);
        }
        
        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.PLUS).own().SIGNI()).get();
            gainPower(target, 3000, ChronoDuration.nextTurnEnd(getOpponent()));
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(7000,0)).get();
            
            if(target != null && payEner(Cost.colorless(1)))
            {
                banish(target);
            }
        }
    }
}
