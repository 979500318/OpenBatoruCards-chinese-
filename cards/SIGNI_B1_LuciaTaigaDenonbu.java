package open.batoru.data.cards;

import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_B1_LuciaTaigaDenonbu extends Card {

    public SIGNI_B1_LuciaTaigaDenonbu()
    {
        setImageSets("WXDi-P14-086");

        setOriginalName("電音部　大賀ルキア");
        setAltNames("デンオンブタイガルキア Denonbu Taiga Rukia");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、手札から＜電音部＞のシグニを１枚捨ててもよい。そうした場合、対戦相手は手札を１枚捨てる。\n\n" +
                "@U：あなたの＜電音部＞のシグニの効果によってこのカードが捨てられたとき、%Xを支払ってもよい。そうした場合、カードを１枚引く。" +
                "~#：カードを３枚引き、手札を１枚捨てる。"
        );

        setName("en", "DEN-ON-BU Lucia Taiga");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, you may discard a <<DEN-ON-BU>> SIGNI. If you do, your opponent discards a card.\n\n@U: When this card is discarded by your <<DEN-ON-BU>> SIGNI's effect, you may pay %X. If you do, draw a card." +
                "~#Draw three cards and discard a card."
        );
        
        setName("en_fan", "Lucia Taiga, Denonbu");
        setDescription("en_fan",
                "@U: Whenever this SIGNI attacks, you may discard 1 <<Denonbu>> SIGNI from your hand. If you do, your opponent discards 1 card from their hand.\n\n" +
                "@U: When this card is discarded by the effect of your <<Denonbu>> SIGNI, you may pay %X. If you do, draw 1 card." +
                "~#Draw 3 cards, and discard 1 card from your hand."
        );

		setName("zh_simplified", "电音部 大贺露琪雅");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，可以从手牌把<<電音部>>精灵1张舍弃。这样做的场合，对战对手把手牌1张舍弃。\n" +
                "@U :当因为你的<<電音部>>精灵的效果把这张牌舍弃时，可以支付%X。这样做的场合，抽1张牌。" +
                "~#抽3张牌，手牌1张舍弃。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DENONBU);
        setLevel(1);
        setPower(2000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff1);

            AutoAbility auto2 = registerAutoAbility(GameEventId.DISCARD, this::onAutoEff2);
            auto2.setCondition(this::onAutoEff2Cond);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private void onAutoEff1()
        {
            if(discard(0,1, new TargetFilter().SIGNI().withClass(CardSIGNIClass.DENONBU)).get() != null)
            {
                discard(getOpponent(), 1);
            }
        }
        
        private ConditionState onAutoEff2Cond()
        {
            return getEvent().getSourceAbility() != null && isOwnCard(getEvent().getSourceCardIndex()) &&
                   getEvent().getSourceCardIndex().getIndexedInstance().getSIGNIClass().matches(CardSIGNIClass.DENONBU) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff2()
        {
            if(payEner(Cost.colorless(1)))
            {
                draw(1);
            }
        }

        private void onLifeBurstEff()
        {
            draw(3);
            discard(1);
        }
    }
}
