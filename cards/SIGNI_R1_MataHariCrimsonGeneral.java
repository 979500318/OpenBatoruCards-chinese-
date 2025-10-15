package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.cost.CoinCost;

public final class SIGNI_R1_MataHariCrimsonGeneral extends Card {

    public SIGNI_R1_MataHariCrimsonGeneral()
    {
        setImageSets("WXDi-P09-056");

        setOriginalName("紅将　マタ・ハリ");
        setAltNames("コウショウマタハリ Koushou Mata Hari");
        setDescription("jp",
                "@E #C #C：対戦相手のパワー3000以下のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Mata Hari, Crimson General");
        setDescription("en",
                "@E #C #C: Vanish target SIGNI on your opponent's field with power 3000 or less."
        );
        
        setName("en_fan", "Mata Hari, Crimson General");
        setDescription("en_fan",
                "@E #C #C: Target 1 of your opponent's SIGNI with power 3000 or less, and banish it."
        );

		setName("zh_simplified", "红将 玛塔·哈丽");
        setDescription("zh_simplified", 
                "@E #C #C:对战对手的力量3000以下的精灵1只作为对象，将其破坏。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VALOR);
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

            registerEnterAbility(new CoinCost(2), this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,3000)).get();
            banish(target);
        }
    }
}
