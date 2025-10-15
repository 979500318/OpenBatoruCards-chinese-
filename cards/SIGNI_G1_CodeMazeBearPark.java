package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.events.EventMove;

public final class SIGNI_G1_CodeMazeBearPark extends Card {

    public SIGNI_G1_CodeMazeBearPark()
    {
        setImageSets("WXDi-D06-012");

        setOriginalName("コードメイズ　クマボク");
        setAltNames("コードメイズクマボク Koodo Meizu Kumaboku");
        setDescription("jp",
                "@U：対戦相手のシグニ１体がアタックしたとき、その正面にシグニがない場合、このシグニをアタックしたそのシグニの正面に配置してもよい。\n" +
                "@U：場にあるこのシグニが他のシグニゾーンに移動したとき、%X %Xを支払ってもよい。そうした場合、このシグニとあなたのエナゾーンにあるシグニ１枚の場所を入れ替える。"
        );

        setName("en", "Noboribetsu, Code: Maze");
        setDescription("en",
                "@U: Whenever a SIGNI on your opponent's field attacks, if there is no SIGNI in front of that SIGNI, you may move this SIGNI to the SIGNI Zone in front of the attacking SIGNI.\n" +
                "@U: When this SIGNI on the field moves to a different SIGNI Zone, you may pay %X %X. If you do, swap this SIGNI and a SIGNI in your Ener Zone."
        );

        setName("en_fan", "Code Maze Bear Park");
        setDescription("en_fan",
                "@U: Whenever 1 of your opponent's SIGNI attacks, if there is no SIGNI in front of it, you may move this SIGNI in front of the attacking SIGNI.\n" +
                "@U: Whenever this SIGNI on the field moves to another SIGNI zone, you may pay %X %X. If you do, exchange the positions of this SIGNI with 1 SIGNI in your ener zone."
        );

		setName("zh_simplified", "迷宫代号 熊熊牧场");
        setDescription("zh_simplified", 
                "@U :当对战对手的精灵1只攻击时，其的正面没有精灵的场合，可以把这只精灵往攻击中的那只精灵的正面配置。\n" +
                "@U :当场上的这只精灵往其他的精灵区移动时，可以支付%X %X。这样做的场合，将这只精灵与你的能量区的精灵1张的场所交换。（其的@E能力发动）\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.LABYRINTH);
        setLevel(1);
        setPower(2000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto1 = registerAutoAbility(GameEventId.ATTACK, this::onAutoEff1);
            auto1.setCondition(this::onAutoEff1Cond);

            AutoAbility auto2 = registerAutoAbility(GameEventId.MOVE, this::onAutoEff2);
            auto2.setCondition(this::onAutoEff2Cond);
        }

        private ConditionState onAutoEff1Cond(CardIndex caller)
        {
            return !isOwnCard(caller) && CardType.isSIGNI(caller.getCardReference().getType()) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            if(getCardIndex().isSIGNIOnField() && caller.isSIGNIOnField() && caller.getIndexedInstance().getOppositeSIGNI() == null && playerChoiceActivate())
            {
                moveToZone(getCardIndex(), CardLocation.getOppositeSIGNILocation(getEvent().getCaller().getLocation()));
            }
        }

        private ConditionState onAutoEff2Cond()
        {
            return CardLocation.isSIGNI(EventMove.getDataMoveLocation()) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff2()
        {
            if(CardLocation.isSIGNI(getCardIndex().getLocation()) && payEner(Cost.colorless(2)))
            {
                CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.MOVE).own().SIGNI().fromEner().playableAs(getCardIndex())).get();
                
                if(cardIndex != null && putInEner(getCardIndex()))
                {
                    putOnField(cardIndex, getCardIndex().getOldLocation());
                }
            }
        }
    }
}
