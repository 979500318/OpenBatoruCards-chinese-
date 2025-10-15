package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_G2_CodeMazeAtDissona extends Card {

    public SIGNI_G2_CodeMazeAtDissona()
    {
        setImageSets("WXDi-P12-079", "SPDi01-111","SPDi27-04");

        setOriginalName("コードメイズ　アト//ディソナ");
        setAltNames("コードメイズアトディソナ Koodo Meizu Ato Disona");
        setDescription("jp",
                "@C：このシグニが中央のシグニゾーンにあるかぎり、このシグニのパワーは＋4000される。\n\n" +
                "@U：このカードがデッキからエナゾーンに置かれたとき、このカードをエナゾーンから手札に加えてもよい。" +
                "~#：【エナチャージ１】をする。このターン、次にあなたがルリグによってダメージを受ける場合、代わりにダメージを受けない。"
        );

        setName("en", "At//Dissona, Code: Maze");
        setDescription("en",
                "@C: As long as this SIGNI is in the center SIGNI Zone, it gets +4000 power.\n\n@U: When this card is put into your Ener Zone from your deck, you may add this card from your Ener Zone to your hand." +
                "~#[[Ener Charge 1]]. The next time you would take damage from a LRIG this turn, instead you do not take that damage."
        );
        
        setName("en_fan", "Code Maze At//Dissona");
        setDescription("en_fan",
                "@C: As long as this SIGNI is in your center SIGNI zone, it gets +4000 power.\n\n" +
                "@U: When this card is put from your deck into your ener zone, you may return this card from your ener zone to your hand." +
                "~#[[Ener Charge 1]]. This turn, the next time you would be damaged by a LRIG, instead you aren't damaged."
        );

		setName("zh_simplified", "迷宫代号 亚特//失调");
        setDescription("zh_simplified", 
                "@C :这只精灵在中央的精灵区时，这只精灵的力量+4000。\n" +
                "@U :当这张牌从牌组放置到能量区时，可以把这张牌从能量区加入手牌。" +
                "~#[[能量填充1]]。这个回合，下一次你因为分身受到伤害的场合，作为替代，不会受到伤害。\n"
        );

        setCardFlags(CardFlag.LIFEBURST | CardFlag.DISSONA);

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.LABYRINTH);
        setLevel(2);
        setPower(8000);

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

            AutoAbility auto = registerAutoAbility(GameEventId.ENER, this::onAutoEff);
            auto.setActiveLocation(CardLocation.DECK_MAIN);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onConstEffCond()
        {
            return getCardIndex().getLocation() == CardLocation.SIGNI_CENTER ? ConditionState.OK : ConditionState.BAD;
        }
        
        private void onAutoEff()
        {
            if(getCardIndex().getLocation() == CardLocation.ENER && playerChoiceActivate())
            {
                addToHand(getCardIndex());
            }
        }

        private void onLifeBurstEff()
        {
            enerCharge(1);

            blockNextDamage(cardIndexSnapshot -> CardType.isLRIG(cardIndexSnapshot.getCardReference().getType()));
        }
    }
}
