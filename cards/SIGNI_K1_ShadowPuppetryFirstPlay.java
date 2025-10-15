package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCostList;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.cost.TrashCost;

public final class SIGNI_K1_ShadowPuppetryFirstPlay extends Card {

    public SIGNI_K1_ShadowPuppetryFirstPlay()
    {
        setImageSets("WX24-P2-090");

        setOriginalName("壱ノ遊　カゲエ");
        setAltNames("イチノユウカゲエ Ichi no Yuu Kague");
        setDescription("jp",
                "@A %X @[このシグニを場からトラッシュに置く]@：あなたのトラッシュからレベル２以上の＜遊具＞のシグニ１枚を対象とし、それを場に出す。"
        );

        setName("en", "Shadow Puppetry, First Play");
        setDescription("en",
                "@A %X @[Put this SIGNI from the field into the trash]@: Target 1 level 2 or higher <<Playground Equipment>> SIGNI from your trash, and put it onto the field."
        );

		setName("zh_simplified", "壹之游 手影戏");
        setDescription("zh_simplified", 
                "@A %X这只精灵从场上放置到废弃区:从你的废弃区把等级2以上的<<遊具>>精灵1张作为对象，将其出场。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.PLAYGROUND_EQUIPMENT);
        setLevel(1);
        setPower(3000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerActionAbility(new AbilityCostList(new TrashCost(), new EnerCost(Cost.colorless(1))), this::onActionEff);
        }
        
        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FIELD).own().SIGNI().withLevel(2,0).withClass(CardSIGNIClass.PLAYGROUND_EQUIPMENT).fromTrash().playable()).get();
            putOnField(target);
        }
    }
}
