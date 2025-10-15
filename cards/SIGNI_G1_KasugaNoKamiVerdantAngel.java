package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.CardAbilities;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_G1_KasugaNoKamiVerdantAngel extends Card {

    public SIGNI_G1_KasugaNoKamiVerdantAngel()
    {
        setImageSets("WXDi-P09-070");

        setOriginalName("翠天　カスガノカミ");
        setAltNames("スイテンカスガノカミ Suiten Kasuga no Kami");
        setDescription("jp",
                "@C：あなたのエナゾーンにあるカードが持つ色が２種類以上あるかぎり、このシグニのパワーは＋4000される。"
        );

        setName("en", "Kasuga no Kami, Jade Angel");
        setDescription("en",
                "@C: As long as you have two or more different colors among cards in your Ener Zone, this SIGNI gets +4000 power."
        );
        
        setName("en_fan", "Kasuga no Kami, Verdant General");
        setDescription("en_fan",
                "@C: As long as there are 2 or more common colors among cards in your ener zone, this SIGNI gets +4000 power."
        );

		setName("zh_simplified", "翠天 春日神");
        setDescription("zh_simplified", 
                "@C :你的能量区的牌持有颜色在合计2种类以上时，这只精灵的力量+4000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANGEL);
        setLevel(1);
        setPower(3000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerConstantAbility(this::onConstEffCond, new PowerModifier(4000));
        }
        
        private ConditionState onConstEffCond()
        {
            return CardAbilities.getColorsCount(getCardsInEner(getOwner())) >= 2 ? ConditionState.OK : ConditionState.BAD;
        }
    }
}
