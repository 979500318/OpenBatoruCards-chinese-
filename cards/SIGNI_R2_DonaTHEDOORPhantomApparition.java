package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCostList;
import open.batoru.data.ability.cost.DiscardCost;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_R2_DonaTHEDOORPhantomApparition extends Card {

    public SIGNI_R2_DonaTHEDOORPhantomApparition()
    {
        setImageSets("WXDi-P15-062");

        setOriginalName("幻怪　ドーナ//THE DOOR");
        setAltNames("ゲンカイドーナザドアー Genkai Doona Za Doaa");
        setDescription("jp",
                "@E %R @[手札から＜解放派＞のシグニを１枚捨てる]@：対戦相手のパワー8000以下のシグニ１体を対象とし、それをバニッシュする。\n" +
                "@E %X：あなたのトラッシュから＜解放派＞のシグニ１枚を対象とし、それをこのシグニの下に置く。"
        );

        setName("en", "Dona//THE DOOR, Phantom Spirit");
        setDescription("en",
                "@E %R @[Discard a <<Liberation Division>> SIGNI]@: Vanish target SIGNI on your opponent's field with power 8000 or less.\n@E %X: Put target <<Liberation Division>> SIGNI from your trash under this SIGNI."
        );
        
        setName("en_fan", "Dona//THE DOOR, Phantom Apparition");
        setDescription("en_fan",
                "@E %R @[Discard 1 <<Liberation Faction>> SIGNI from your hand]@: Target 1 of your opponent's SIGNI with power 8000 or less, and banish it.\n" +
                "@E %X: Target 1 <<Liberation Faction>> SIGNI from your trash, and put it under this SIGNI."
        );

		setName("zh_simplified", "幻怪 多娜//THE DOOR");
        setDescription("zh_simplified", 
                "@E %R从手牌把<<解放派>>精灵1张舍弃:对战对手的力量8000以下的精灵1只作为对象，将其破坏。\n" +
                "@E %X:从你的废弃区把<<解放派>>精灵1张作为对象，将其放置到这只精灵的下面。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.LIBERATION_FACTION,CardSIGNIClass.APPARITION);
        setLevel(2);
        setPower(8000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(new AbilityCostList(
                new EnerCost(Cost.color(CardColor.RED, 1)),
                new DiscardCost(new TargetFilter().SIGNI().withClass(CardSIGNIClass.LIBERATION_FACTION))
            ), this::onEnterEff1);
            
            registerEnterAbility(new EnerCost(Cost.colorless(1)), this::onEnterEff2);
        }
        
        private void onEnterEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,8000)).get();
            banish(target);
        }
        
        private void onEnterEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.UNDER).own().SIGNI().withClass(CardSIGNIClass.LIBERATION_FACTION).fromTrash()).get();
            attach(getCardIndex(), target, CardUnderType.UNDER_GENERIC);
        }
    }
}
