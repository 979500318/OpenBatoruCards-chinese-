package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.DataTable;

public final class SIGNI_R2_HenmenMediumTrap extends Card {

    public SIGNI_R2_HenmenMediumTrap()
    {
        setImageSets("WX24-P4-067");

        setOriginalName("中罠　ヘンメン");
        setAltNames("チュウビンヘンメン Chuubin Henmen");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、このシグニと同じシグニゾーンにある【マジックボックス】１つを表向きにしトラッシュに置いてもよい。その後、そのカードが##を持つ場合、対戦相手のパワー5000以下のシグニ１体を対象とし、それをバニッシュする。##を持たない場合、このアタックを無効にし、対戦相手のエナゾーンから対戦相手のセンタールリグと共通する色を持たないカードを３枚まで対象とし、それらをトラッシュに置く。" +
                "~#：対戦相手のパワー8000以下のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Henmen, Medium Trap");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, you may put 1 [[Magic Box]] in the same SIGNI zone as this SIGNI face up into the trash. Then, if that card has a ## @[Life Burst]@, target 1 of your opponent's SIGNI with power 5000 or less, and banish it. If it doesn't have a ## @[Life Burst]@, disable that attack, target up to 3 cards from your opponent's ener zone that don't share a common color with your opponent's center LRIG, and put them into the trash." +
                "~#Target 1 of your opponent's SIGNI with power 8000 or less, and banish it."
        );

		setName("zh_simplified", "中罠 变脸");
        setDescription("zh_simplified", 
                "@U 当这只精灵攻击时，可以把与这只精灵相同精灵区的[[魔术箱]]1个表向并放置到废弃区。然后，那张牌持有##的场合，对战对手的力量5000以下的精灵1只作为对象，将其破坏。不持有##的场合，这次攻击无效，从对战对手的能量区把不持有与对战对手的核心分身共通颜色的牌3张最多作为对象，将这些放置到废弃区。" +
                "~#对战对手的力量8000以下的精灵1只作为对象，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.TRICK);
        setLevel(2);
        setPower(8000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private void onAutoEff()
        {
            DataTable<CardIndex> data = new TargetFilter().own().withUnderType(CardUnderType.ZONE_MAGIC_BOX).fromSafeLocation(getCardIndex().getLocation()).getExportedData();
            if(!data.isEmpty() && playerChoiceActivate() && trash(data) > 0)
            {
                data.get().getIndexedInstance().findLifeBurstAbility().ifPresentOrElse(ability -> {
                    CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,5000)).get();
                    banish(target);
                }, () -> {
                    disableNextAttack(getCardIndex());
                    
                    DataTable<CardIndex> dataEner = playerTargetCard(0,3, new TargetFilter(TargetHint.BURN).OP().fromEner().not(new TargetFilter().withColor(getLRIG(getOpponent()).getIndexedInstance().getColor())));
                    trash(dataEner);
                });
            }
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,8000)).get();
            banish(target);
        }
    }
}
