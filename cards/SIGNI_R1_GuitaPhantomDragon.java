package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_R1_GuitaPhantomDragon extends Card {

    public SIGNI_R1_GuitaPhantomDragon()
    {
        setImageSets("WX24-P2-067");

        setOriginalName("幻竜　ギータ");
        setAltNames("ゲンリュウギータ Genryuu Giita");
        setDescription("jp",
                "@C：対戦相手のエナゾーンにあるカードが３枚以下であるかぎり、このシグニのパワーは＋4000される。" +
                "~#：手札を１枚捨て、カードを３枚引く。"
        );

        setName("en", "Guita, Phantom Dragon");
        setDescription("en",
                "@C: As long as there are 3 or less cards in your opponent's ener zone, this SIGNI gets +4000 power." +
                "~#Discard 1 card from your hand, and draw 3 cards."
        );

		setName("zh_simplified", "幻龙 巨骡龙");
        setDescription("zh_simplified", 
                "@C :对战对手的能量区的牌在3张以下时，这只精灵的力量+4000。" +
                "~#手牌1张舍弃，抽3张牌。（即使没有手牌舍弃也能抽牌）\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DRAGON_BEAST);
        setLevel(1);
        setPower(3000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerConstantAbility(this::onConstEffCond, new PowerModifier(4000));
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onConstEffCond()
        {
            return getEnerCount(getOpponent()) <= 3 ? ConditionState.OK : ConditionState.BAD;
        }
        
        private void onLifeBurstEff()
        {
            discard(1);
            draw(3);
        }
    }
}
