package open.batoru.data.cards;

import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.PowerModifier;

public class SIGNI_K1_DeusDissonaSmallEquipment extends Card {

    public SIGNI_K1_DeusDissonaSmallEquipment()
    {
        setImageSets("WXDi-P13-083");

        setOriginalName("小装　デウス//ディソナ");
        setAltNames("ショウソウデウスディソナ Shousou Deusu Disona");
        setDescription("jp",
                "@C：このシグニの下に#Sのカードがあるかぎり、このシグニのパワーは＋5000される。\n" +
                "@E：あなたのデッキの一番上のカードをこのシグニの下に置く。"
        );

        setName("en", "Deus//Dissona, Lightly Armed");
        setDescription("en",
                "@C: As long as there is a #S card underneath this SIGNI, it gets +5000 power.\n@E: Put the top card of your deck under this SIGNI."
        );
        
        setName("en_fan", "Deus//Dissona, Small Equipment");
        setDescription("en_fan",
                "@C: As long as there is a #S @[Dissona]@ card under this SIGNI, this SIGNI gets +5000 power.\n" +
                "@E: Put the top card of your deck under this SIGNI."
        );

		setName("zh_simplified", "小装 迪乌斯//失调");
        setDescription("zh_simplified", 
                "@C 这只精灵的下面有#S的牌时，这只精灵的力量+5000。\n" +
                "@E :你的牌组最上面的牌放置到这只精灵的下面。\n"
        );

        setCardFlags(CardFlag.DISSONA);

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ARM);
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

            registerConstantAbility(this::onConstEffCond, new PowerModifier(5000));
            
            registerEnterAbility(this::onEnterEff);
        }

        private ConditionState onConstEffCond()
        {
            return new TargetFilter().own().dissona().under(getCardIndex()).getValidTargetsCount() > 0 ? ConditionState.OK : ConditionState.BAD;
        }
        
        private void onEnterEff()
        {
            attach(getCardIndex(), CardLocation.DECK_MAIN, CardUnderType.UNDER_GENERIC);
        }
    }
}
