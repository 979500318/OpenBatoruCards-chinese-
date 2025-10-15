package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardFlag;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.SpellAbility;

public final class SPELL_X_GuidepostToTheFuture extends Card {

    public SPELL_X_GuidepostToTheFuture()
    {
        setImageSets("WXDi-P16-094");

        setOriginalName("未来への道標");
        setAltNames("ミライヘノミチシルベ Mirai he no Michishirube");
        setDescription("jp",
                "あなたの場にいるルリグ３体が同じチームの場合、以下の２つから１つを選ぶ。\n" +
                "$$1あなたのトラッシュから=Tを持つシグニ１枚を対象とし、それを場に出す。次の対戦相手のターン終了時まで、それのパワーを＋3000する。\n" +
                "$$2あなたのデッキの上からカードを５枚見る。その中からカードを２枚までエナゾーンに置き、残りを好きな順番でデッキの一番下に置く。" +
                "~#：あなたのトラッシュからシグニ１枚を対象とし、それを手札に加える。"
        );

        setName("en", "Beacon to the Future");
        setDescription("en",
                "If the three LRIG on your field are on the same team, choose one of the following.\n$$1Put target SIGNI with =T from your trash onto your field. It gets +3000 power until the end of your opponent's next end phase.\n$$2Look at the top five cards of your deck. Put up to two cards from among them into your Ener Zone. Put the rest on the bottom of your deck in any order." +
                "~#Add target SIGNI from your trash to your hand."
        );
        
        setName("en_fan", "Guidepost to the Future");
        setDescription("en_fan",
                "If 3 of your LRIG on the field are all on the same team, @[@|choose 1 of the following:|@]@\n" +
                "$$1 Target 1 =T SIGNI from your trash, and put it onto the field. Until the end of your opponent's next turn, it gets +3000 power.\n" +
                "$$2 Look at the top 5 cards of your deck. Put up to 2 cards from among them into the ener zone, and put the rest on the bottom of your deck in any order." +
                "~#Target 1 SIGNI from your trash, and add it to your hand."
        );

		setName("zh_simplified", "向未来的道标");
        setDescription("zh_simplified", 
                "你的场上的分身3只是相同队伍的场合，从以下的2种选1种。\n" +
                "$$1 从你的废弃区把持有=T的精灵1张作为对象，将其出场。直到下一个对战对手的回合结束时为止，其的力量+3000。\n" +
                "$$2 从你的牌组上面看5张牌。从中把牌2张最多放置到能量区，剩下的任意顺序放置到牌组最下面。" +
                "~#从你的废弃区把精灵1张作为对象，将其加入手牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SPELL);
        setCost(Cost.colorless(1));

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        private final SpellAbility spell;
        public IndexedInstance(int cardId)
        {
            super(cardId);

            spell = registerSpellAbility(this::onSpellEff);
            spell.setModeChoice(1);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onSpellEff()
        {
            DataTable<CardIndex> dataLRIG = getLRIGs(getOwner());
            if(dataLRIG.size() == 3 && dataLRIG.stream().map(c -> c.getCardReference().getLRIGTeam()).distinct().count() == 1)
            {
                if(spell.getChosenModes() == 1)
                {
                    CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FIELD).own().SIGNI().startsWithDescription("=T").fromTrash().playable()).get();

                    if(putOnField(target))
                    {
                        gainPower(target, 3000, ChronoDuration.nextTurnEnd(getOpponent()));
                    }
                } else {
                    look(5);

                    DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.ENER).own().fromLooked());
                    putInEner(data);

                    while(getLookedCount() > 0)
                    {
                        CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                        returnToDeck(cardIndex, DeckPosition.BOTTOM);
                    }
                }
            }
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().fromTrash()).get();
            addToHand(target);
        }
    }
}
