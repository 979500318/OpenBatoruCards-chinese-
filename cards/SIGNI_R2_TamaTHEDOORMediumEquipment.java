package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_R2_TamaTHEDOORMediumEquipment extends Card {

    public SIGNI_R2_TamaTHEDOORMediumEquipment()
    {
        setImageSets("WXDi-P16-067");

        setOriginalName("中装　タマ//THE DOOR");
        setAltNames("チュウソウタマザドアー Chuusou Tama Za Doaa");
        setDescription("jp",
                "@E %X：あなたのトラッシュから＜解放派＞のシグニ１枚を対象とし、それをこのシグニの下に置く。" +
                "~#：対戦相手のパワー8000以下のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Tama//THE DOOR, High Armed");
        setDescription("en",
                "@E %X: Put target <<Liberation Division>> SIGNI from your trash under this SIGNI." +
                "~#Vanish target SIGNI on your opponent's field with power 8000 or less."
        );
        
        setName("en_fan", "Tama//THE DOOR, Medium Equipment");
        setDescription("en_fan",
                "@E %X: Target 1 <<Liberation Faction>> SIGNI from your trash, and put it under this SIGNI." +
                "~#Target 1 of your opponent's SIGNI with power 8000 or less, and banish it."
        );

		setName("zh_simplified", "中装 小玉//THE DOOR");
        setDescription("zh_simplified", 
                "@E %X:从你的废弃区把<<解放派>>精灵1张作为对象，将其放置到这只精灵的下面。" +
                "~#对战对手的力量8000以下的精灵1只作为对象，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.LIBERATION_FACTION,CardSIGNIClass.ARM);
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

            registerEnterAbility(new EnerCost(Cost.colorless(1)), this::onEnterEff);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.UNDER).own().SIGNI().withClass(CardSIGNIClass.LIBERATION_FACTION).fromTrash()).get();
            attach(getCardIndex(), target, CardUnderType.UNDER_GENERIC);
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0, 8000)).get();
            banish(target);
        }
    }
}
