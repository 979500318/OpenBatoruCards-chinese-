package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.cost.TrashCost;

public final class SIGNI_G3_HellgateDissonaVerdantDevil extends Card {

    public SIGNI_G3_HellgateDissonaVerdantDevil()
    {
        setImageSets("WXDi-P13-081");

        setOriginalName("翠魔　ヘルゲト//ディソナ");
        setAltNames("スイマヘルゲトディソナ Suima Herugeto Disona");
        setDescription("jp",
                "@E @[エナゾーンから#Sのカード５枚をトラッシュに置く]@：対戦相手のレベル３以上のシグニを２体まで対象とし、それらをバニッシュする。"
        );

        setName("en", "Hellsgate//Dissona, Jade Evil");
        setDescription("en",
                "@E @[Put five #S cards from your Ener Zone into your trash]@: Vanish up to two target level three or more SIGNI on your opponent's field."
        );
        
        setName("en_fan", "Hellgate//Dissona, Verdant Devil");
        setDescription("en_fan",
                "@E @[Put 5 #S @[Dissona]@ cards from your ener zone into the trash]@: Target up to 2 of your opponent's level 3 or higher SIGNI, and banish them."
        );

		setName("zh_simplified", "翠魔 地狱门//失调");
        setDescription("zh_simplified", 
                "@E 从能量区把#S的牌5张放置到废弃区:对战对手的等级3以上的精灵2只最多作为对象，将这些破坏。\n"
        );

        setCardFlags(CardFlag.DISSONA);

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEVIL);
        setLevel(3);
        setPower(12000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(new TrashCost(5, new TargetFilter().dissona().fromEner()), this::onEnterEff);
        }

        private void onEnterEff()
        {
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.BANISH).OP().SIGNI().withLevel(3,0));
            banish(data);
        }
    }
}
