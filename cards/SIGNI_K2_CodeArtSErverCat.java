package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.cost.TrashCost;

public final class SIGNI_K2_CodeArtSErverCat extends Card {

    public SIGNI_K2_CodeArtSErverCat()
    {
        setImageSets("WX25-P2-104");

        setOriginalName("コードアート　Hイゼンネコ");
        setAltNames("コードアートエイチイゼンネコ Koodo Aato Eichi Izen Neko Server Cat Server");
        setDescription("jp",
                "@E @[エナゾーンから＜電機＞のシグニ１枚をトラッシュに置く]@：あなたのトラッシュから黒のスペル１枚を対象とし、それを手札に加える。"
        );

        setName("en", "Code Art S Erver Cat");
        setDescription("en",
                "@E @[Put 1 <<Electric Machine>> SIGNI from your ener zone into the trash]@: Target 1 black spell from your trash, and add it to your hand."
        );

		setName("zh_simplified", "必杀代号 送餐猫");
        setDescription("zh_simplified", 
                "@E 从能量区把<<電機>>精灵1张放置到废弃区:从你的废弃区把黑色的魔法1张作为对象，将其加入手牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ELECTRIC_MACHINE);
        setLevel(2);
        setPower(5000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(new TrashCost(new TargetFilter().SIGNI().withClass(CardSIGNIClass.ELECTRIC_MACHINE).fromEner()), this::onEnterEff);
        }

        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().spell().withColor(CardColor.BLACK).fromTrash()).get();
            addToHand(target);
        }
    }
}
