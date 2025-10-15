package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_G4_NekomusuPhantomApparition extends Card {

    public SIGNI_G4_NekomusuPhantomApparition()
    {
        setImageSets("WDK03-012");

        setOriginalName("幻怪　ネコムス");
        setAltNames("ゲンカイネコムス Genkai Nekomusu");
        setDescription("jp",
                "@E：このシグニが左のシグニゾーンに出たとき、【エナチャージ１】をする。\n" +
                "@E %G：あなたのエナゾーンからシグニ１枚を対象とし、それを手札に加える。"
        );

        setName("en", "Nekomusu, Phantom Apparition");
        setDescription("en",
                "@E: If this SIGNI entered the field in your left SIGNI zone, [[Ener Charge 1]].\n" +
                "@E %G: Target 1 SIGNI from your ener zone, and add it to your hand."
        );

		setName("zh_simplified", "幻怪 慕斯猫");
        setDescription("zh_simplified", 
                "@E :当这只精灵在左侧的精灵区出场时，[[能量填充1]]。\n" +
                "@E %G:从你的能量区把精灵1张作为对象，将其加入手牌。\n"
        );

        setLRIGType(CardLRIGType.MIDORIKO);
        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClass.APPARITION);
        setLevel(4);
        setPower(10000);

        setPlayFormat(PlayFormat.KEY);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(this::onEnterEff1);
            registerEnterAbility(new EnerCost(Cost.color(CardColor.GREEN, 1)), this::onEnterEff2);
        }
        
        private void onEnterEff1()
        {
            if(getCardIndex().getLocation() == CardLocation.SIGNI_LEFT)
            {
                enerCharge(1);
            }
        }
        
        private void onEnterEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().fromEner()).get();
            addToHand(target);
        }
    }
}
