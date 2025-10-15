package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.CardIndexSnapshot;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.cost.PutInEnerCost;

public final class SIGNI_G2_AiyaiDissonaSecondPlay extends Card {

    public SIGNI_G2_AiyaiDissonaSecondPlay()
    {
        setImageSets("WXDi-P16-080");

        setOriginalName("弍ノ遊　アイヤイ//ディソナ");
        setAltNames("ニノユウアイヤイディソナ Ni no Yuu Aiyai Disona");
        setDescription("jp",
                "@E @[手札からシグニ１枚をエナゾーンに置く]@：あなたのエナゾーンからこの方法でエナゾーンに置いたシグニと同じレベルのシグニ１枚を対象とし、それを手札に加える。"
        );

        setName("en", "Aiyai//Dissona, Second Play");
        setDescription("en",
                "@E @[Put a SIGNI from your hand into your Ener Zone]@: Add target SIGNI from your Ener Zone with the same level as the SIGNI put into your Ener Zone this way to your hand."
        );
        
        setName("en_fan", "Aiyai//Dissona, Second Play");
        setDescription("en_fan",
                "@E @[Put 1 SIGNI from your hand into the ener zone]@: Target 1 SIGNI from your ener zone with the same level as the SIGNI put into the ener zone this way, and add it to your hand."
        );

		setName("zh_simplified", "贰之游 艾娅伊//失调");
        setDescription("zh_simplified", 
                "@E 从手牌把精灵1张放置到能量区:从你的能量区把与这个方法放置到能量区的精灵相同等级的精灵1张作为对象，将其加入手牌。\n"
        );

        setCardFlags(CardFlag.DISSONA);

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.PLAYGROUND_EQUIPMENT);
        setLevel(2);
        setPower(5000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(new PutInEnerCost(new TargetFilter().SIGNI().fromHand()), this::onEnterEff);
        }

        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().withLevel(((CardIndexSnapshot)getAbility().getCostPaidData().get()).getLevel().getValue()).fromEner()).get();
            addToHand(target);
        }
    }
}
