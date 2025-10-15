package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.ConstantAbilityShared;
import open.batoru.data.ability.EnterAbility;
import open.batoru.data.ability.modifiers.PowerModifier;
import open.batoru.data.CardDataImageSet.Mask;

public final class LRIGA_W2_HanakoUrawaLetTheForbiddenGamesBegin extends Card {

    public LRIGA_W2_HanakoUrawaLetTheForbiddenGamesBegin()
    {
        setImageSets(Mask.PORTRAIT_OFFSET_RIGHT+"WXDi-CP02-026");

        setOriginalName("浦和ハナコ[禁じられた遊びを始めましょう]");
        setAltNames("ウラワハナコキンジラレタアソビヲハジメマショウ Urawa Hanako Kinjirareta Asobi wo Hajimemashou");
        setDescription("jp",
                "@E：次の対戦相手のターン終了時まで、このルリグは@>@C：あなたのシグニのパワーを＋5000する。@@を得る。\n" +
                "@E：あなたのトラッシュから#Gを持つシグニ１枚を対象とし、それを手札に加える。" +
                "~{{E：あなたのデッキの上からカードを７枚見る。その中から＜ブルアカ＞のカード１枚を公開し手札に加え、残りをシャッフルしてデッキの一番下に置く。"
        );

        setName("en", "Urawa Hanako [Want to Break the Rules?]");
        setDescription("en",
                "@E: This LRIG gains@>@C: SIGNI on your field get +5000 power.@@until the end of your opponent's next end phase.\n@E: Add target SIGNI with a #G from your trash to your hand.~{{E: Look at the top seven cards of your deck. Reveal a <<Blue Archive>> card from among them and add it to your hand. Put the rest on the bottom of your deck in a random order."
        );
        
        setName("en_fan", "Hanako Urawa [Let the Forbidden Games Begin]");
        setDescription("en_fan",
                "@E: Until the end of your opponent's next turn, this LRIG gains:" +
                "@>@C: All of your SIGNI get +5000 power.@@" +
                "@E: Target 1 #G @[Guard]@ SIGNI from your trash, and add it to your hand." +
                "~{{E: Look at the top 7 cards of your deck. Reveal 1 <<Blue Archive>> card from among them, add it to your hand, and shuffle the rest and put them on the bottom of your deck."
        );

		setName("zh_simplified", "浦和花子[禁忌的游戏要开始了喔~]");
        setDescription("zh_simplified", 
                "@E :直到下一个对战对手的回合结束时为止，这只分身得到\n" +
                "@>@C :你的精灵的力量+5000。@@\n" +
                "@E 从你的废弃区把持有#G的精灵1张作为对象，将其加入手牌。\n" +
                "~{{E:从你的牌组上面看7张牌。从中把<<ブルアカ>>牌1张公开加入手牌，剩下的洗切放置到牌组最下面。@@\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.MAKEUP_WORK_CLUB);
        setColor(CardColor.WHITE);
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(this::onEnterEff1);
            registerEnterAbility(this::onEnterEff2);

            EnterAbility enter3 = registerEnterAbility(this::onEnterEff3);
            enter3.getFlags().addValue(AbilityFlag.BONDED);
        }

        private void onEnterEff1()
        {
            ConstantAbility attachedConst = new ConstantAbilityShared(new TargetFilter().own().SIGNI(), new PowerModifier(5000));
            attachAbility(getCardIndex(), attachedConst, ChronoDuration.nextTurnEnd(getOpponent()));
        }
        
        private void onEnterEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().withState(CardStateFlag.CAN_GUARD).fromTrash()).get();
            addToHand(target);
        }
        
        private void onEnterEff3()
        {
            look(7);
            
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().withClass(CardSIGNIClass.BLUE_ARCHIVE).fromLooked()).get();
            reveal(cardIndex);
            addToHand(cardIndex);

            int countReturned = returnToDeck(getCardsInLooked(getOwner()), DeckPosition.BOTTOM);
            shuffleDeck(countReturned, DeckPosition.BOTTOM);
        }
    }
}

